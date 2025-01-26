package bck_instapic.file.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Ensure the upload directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // Get the original filename and sanitize it
            String originalFileName = Paths.get(file.getOriginalFilename()).getFileName().toString();

            // Generate a unique filename to prevent overwriting
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            // Create the destination file path
            Path filePath = uploadPath.resolve(uniqueFileName);

            // Save the file to the destination path
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Log the file details
            System.out.println("Received file: " + originalFileName + ", size: " + file.getSize() + " bytes");
            System.out.println("Saved file to: " + filePath.toAbsolutePath().toString());

            // Return a success response
            return ResponseEntity.ok("{\"message\": \"File uploaded successfully\"}");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Failed to upload file\"}");
        }
    }
}
