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
 * UploadUtil — saves uploaded images into the webapp Resource folder.
 *
 * Strategy:
 *   1. Save to the DEPLOYED folder (getRealPath) so the image is immediately
 *      accessible via the running Tomcat instance.
 *   2. Also save to the SOURCE folder (src/main/webapp/Resource) so the image
 *      survives the next Maven build / redeploy.
 *
 * Folder layout:
 *   Resource/
 *     profiles/   ← admin & user profile pictures
 *     menu/       ← menu item images
 *
 * Returned path stored in DB:  /Resource/profiles/admin_1_123.jpg
 * Render in JSP:
 *   <img src="<%= request.getContextPath() %><%= user.getProfileImage() %>" />
 */
public final class UploadUtil {

    private UploadUtil() {}

    /**
     * Save an uploaded file to both the deployed webapp folder AND the source folder.
     *
     * @param part      the uploaded Part from the multipart request
     * @param ctx       the ServletContext (resolves deployed path and source path)
     * @param subFolder "profiles" or "menu"
     * @param prefix    filename prefix e.g. "admin_1", "user_3", "item"
     * @return          context-relative URL e.g. "/Resource/profiles/admin_1_123.jpg"
     *                  or null if nothing was uploaded
     */
    public static String save(Part part, ServletContext ctx,
                              String subFolder, String prefix) throws IOException {

        if (part == null || part.getSize() == 0) {
            System.out.println("[UploadUtil] No file uploaded.");
            return null;
        }

        String originalName = part.getSubmittedFileName();
        if (originalName == null || originalName.isBlank()) {
            System.out.println("[UploadUtil] Filename is blank.");
            return null;
        }

        // File extension
        int dotIdx = originalName.lastIndexOf('.');
        String ext = (dotIdx >= 0) ? originalName.substring(dotIdx).toLowerCase() : ".jpg";

        // Unique filename
        String fileName = prefix + "_" + System.currentTimeMillis() + ext;

        // ── 1. Save to DEPLOYED folder (immediately accessible) ──────────────
        String deployedDir = ctx.getRealPath("/Resource/" + subFolder);
        if (deployedDir != null) {
            File dir = new File(deployedDir);
            if (!dir.exists()) dir.mkdirs();
            try (InputStream in = part.getInputStream()) {
                Files.copy(in, Paths.get(deployedDir, fileName),
                           StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("[UploadUtil] Saved to deployed: " + deployedDir + "/" + fileName);
        } else {
            System.err.println("[UploadUtil] getRealPath returned null — WAR not unpacked.");
        }

        // ── 2. Save to SOURCE folder (survives redeploy) ──────────────────────
        // Walk up from the deployed path to find src/main/webapp/Resource
        // Deployed:  .../target/OrderManagementSystem/Resource/profiles/
        // Source:    .../src/main/webapp/Resource/profiles/
        try {
            String sourceDir = findSourceResourceDir(ctx, subFolder);
            if (sourceDir != null) {
                File srcDir = new File(sourceDir);
                if (!srcDir.exists()) srcDir.mkdirs();
                // Re-read the part stream from the deployed copy we just saved
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
            // Non-fatal — deployed copy already saved above
            System.out.println("[UploadUtil] Could not copy to source folder: " + e.getMessage());
        }

        return "/Resource/" + subFolder + "/" + fileName;
    }

    /**
     * Attempt to locate the src/main/webapp/Resource/{subFolder} directory
     * by walking up from the deployed path.
     */
    private static String findSourceResourceDir(ServletContext ctx, String subFolder) {
        String deployedRoot = ctx.getRealPath("/");
        if (deployedRoot == null) return null;

        // Walk up the directory tree looking for src/main/webapp
        File dir = new File(deployedRoot);
        for (int i = 0; i < 8; i++) {
            File candidate = new File(dir, "src/main/webapp/Resource/" + subFolder);
            if (candidate.exists() || new File(dir, "src/main/webapp").exists()) {
                return candidate.getAbsolutePath();
            }
            dir = dir.getParentFile();
            if (dir == null) break;
        }
        return null;
    }
}
