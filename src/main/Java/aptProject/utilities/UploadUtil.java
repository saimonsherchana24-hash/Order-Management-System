package aptProject.utilities;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * UploadUtil — utility class for saving uploaded image files to the server.
 *
 * <p>Images are written to two locations to handle both immediate availability
 * and persistence across Maven redeployments:</p>
 * <ol>
 *   <li><b>Deployed folder</b> ({@code getRealPath}) — makes the image immediately
 *       accessible via the running Tomcat instance without a restart.</li>
 *   <li><b>Source folder</b> ({@code src/main/webapp/Resource}) — ensures the image
 *       is not lost when the project is rebuilt and redeployed.</li>
 * </ol>
 *
 * <p>Folder layout inside {@code Resource/}:</p>
 * <ul>
 *   <li>{@code profiles/} — admin and user profile pictures</li>
 *   <li>{@code menu/}     — menu item images</li>
 * </ul>
 *
 * <p>The returned path (e.g. {@code /Resource/profiles/user_1_123.jpg}) is stored
 * in the database and used in JSP {@code <img>} tags like:</p>
 * <pre>
 *   &lt;img src="&lt;%= request.getContextPath() %&gt;&lt;%= user.getProfileImage() %&gt;" /&gt;
 * </pre>
 */
public final class UploadUtil {

    /** Private constructor — this class should never be instantiated. */
    private UploadUtil() {}

    /**
     * Saves an uploaded file part to both the deployed webapp folder and the source folder.
     *
     * <p>Returns {@code null} (without throwing) if no file was actually uploaded,
     * so callers can safely keep the existing image path unchanged.</p>
     *
     * @param part      the uploaded {@link Part} from the multipart HTTP request
     * @param ctx       the {@link ServletContext} used to resolve the deployed and source paths
     * @param subFolder the target sub-folder name: {@code "profiles"} or {@code "menu"}
     * @param prefix    a filename prefix that identifies the owner, e.g. {@code "user_3"} or {@code "item"}
     * @return the context-relative URL of the saved file (e.g. {@code /Resource/profiles/user_3_123.jpg}),
     *         or {@code null} if no file was uploaded
     * @throws IOException if writing the file to disk fails
     */
    public static String save(Part part, ServletContext ctx,
                              String subFolder, String prefix) throws IOException {

        // Return null if no file was selected or the file is empty
        if (part == null || part.getSize() == 0) {
            System.out.println("[UploadUtil] No file uploaded.");
            return null;
        }

        String originalName = part.getSubmittedFileName();
        // Return null if the browser did not provide a filename
        if (originalName == null || originalName.isBlank()) {
            System.out.println("[UploadUtil] Filename is blank.");
            return null;
        }

        // Extract the file extension (e.g. ".jpg", ".png"); default to ".jpg" if missing
        int dotIdx = originalName.lastIndexOf('.');
        String ext = (dotIdx >= 0) ? originalName.substring(dotIdx).toLowerCase() : ".jpg";

        // Build a unique filename using the prefix and current timestamp to avoid collisions
        String fileName = prefix + "_" + System.currentTimeMillis() + ext;

        // ── Step 1: Save to the DEPLOYED folder (immediately accessible) ─────
        String deployedDir = ctx.getRealPath("/Resource/" + subFolder);
        if (deployedDir != null) {
            File dir = new File(deployedDir);
            if (!dir.exists()) dir.mkdirs(); // create the directory if it doesn't exist yet

            // Copy the uploaded bytes from the request stream to the target file
            try (InputStream in = part.getInputStream()) {
                Files.copy(in, Paths.get(deployedDir, fileName),
                           StandardCopyOption.REPLACE_EXISTING); // overwrite if same name exists
            }
            System.out.println("[UploadUtil] Saved to deployed: " + deployedDir + "/" + fileName);
        } else {
            // getRealPath returns null when the WAR is not unpacked (e.g. embedded Tomcat)
            System.err.println("[UploadUtil] getRealPath returned null — WAR not unpacked.");
        }

        // ── Step 2: Copy to the SOURCE folder (survives Maven redeploy) ──────
        // Walk up from the deployed path to find src/main/webapp/Resource
        // Deployed:  .../target/OrderManagementSystem/Resource/profiles/
        // Source:    .../src/main/webapp/Resource/profiles/
        try {
            String sourceDir = findSourceResourceDir(ctx, subFolder);
            if (sourceDir != null) {
                File srcDir = new File(sourceDir);
                if (!srcDir.exists()) srcDir.mkdirs(); // create source directory if needed

                // Copy from the deployed file we just saved (the Part stream is already consumed)
                if (deployedDir != null) {
                    File deployedFile = new File(deployedDir, fileName);
                    if (deployedFile.exists()) {
                        Files.copy(deployedFile.toPath(),
                                   Paths.get(sourceDir, fileName),
                                   StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("[UploadUtil] Copied to source: " + sourceDir + "/" + fileName);
                    }
                }
            }
        } catch (Exception e) {
            // Failure here is non-fatal — the deployed copy is already saved and accessible
            System.out.println("[UploadUtil] Could not copy to source folder: " + e.getMessage());
        }

        // Return the context-relative URL that will be stored in the database
        return "/Resource/" + subFolder + "/" + fileName;
    }

    /**
     * Attempts to locate the {@code src/main/webapp/Resource/{subFolder}} directory
     * by walking up the directory tree from the deployed webapp root.
     *
     * <p>This is needed because Tomcat deploys to a {@code target/} directory, but
     * we also want to write to the original source tree so files survive a rebuild.</p>
     *
     * @param ctx       the {@link ServletContext} used to get the deployed root path
     * @param subFolder the sub-folder to locate (e.g. "profiles" or "menu")
     * @return the absolute path to the source sub-folder, or {@code null} if not found
     */
    private static String findSourceResourceDir(ServletContext ctx, String subFolder) {
        String deployedRoot = ctx.getRealPath("/");
        if (deployedRoot == null) return null; // can't determine deployed path

        // Walk up the directory tree (up to 8 levels) looking for src/main/webapp
        File dir = new File(deployedRoot);
        for (int i = 0; i < 8; i++) {
            File candidate = new File(dir, "src/main/webapp/Resource/" + subFolder);
            // Return as soon as we find the target folder or its parent webapp directory
            if (candidate.exists() || new File(dir, "src/main/webapp").exists()) {
                return candidate.getAbsolutePath();
            }
            dir = dir.getParentFile(); // move one level up
            if (dir == null) break;    // reached the filesystem root — stop searching
        }
        return null; // source directory not found
    }
}
