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
 * UploadUtil — saves uploaded files into the webapp's Resource folder.
 *
 * Storage layout inside webapp:
 *   Resource/
 *     profiles/   ← admin & user profile pictures
 *     menu/       ← menu item images
 *
 * The returned path is a context-relative URL, e.g.:
 *   /Resource/profiles/admin_1_1234567890.jpg
 *
 * In JSP, render it as:
 *   <img src="<%= request.getContextPath() %><%= user.getProfileImage() %>" />
 */
public final class UploadUtil {

    private UploadUtil() {}

    /**
     * Save an uploaded file into webapp/Resource/{subFolder}/
     *
     * @param part       the uploaded Part from the multipart request
     * @param ctx        the ServletContext (used to resolve the real disk path)
     * @param subFolder  "profiles" or "menu"
     * @param prefix     filename prefix e.g. "admin_1" or "user_3" or "menu"
     * @return           context-relative URL e.g. "/Resource/profiles/admin_1_123.jpg"
     *                   or null if no file was uploaded
     */
    public static String save(Part part, ServletContext ctx,
                              String subFolder, String prefix) throws IOException {

        if (part == null || part.getSize() == 0) return null;

        String originalName = part.getSubmittedFileName();
        if (originalName == null || originalName.isBlank()) return null;

        // Get file extension
        int dotIdx = originalName.lastIndexOf('.');
        String ext = (dotIdx >= 0) ? originalName.substring(dotIdx).toLowerCase() : ".jpg";

        // Unique filename
        String fileName = prefix + "_" + System.currentTimeMillis() + ext;

        // Resolve real disk path: .../webapps/OrderManagementSystem/Resource/{subFolder}/
        String realDir = ctx.getRealPath("/Resource/" + subFolder);
        File dir = new File(realDir);
        if (!dir.exists()) dir.mkdirs();

        // Write file to disk
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, Paths.get(realDir, fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        // Return context-relative URL (no context path prefix — add that in JSP)
        return "/Resource/" + subFolder + "/" + fileName;
    }
}