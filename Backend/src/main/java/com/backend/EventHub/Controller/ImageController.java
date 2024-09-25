package com.backend.EventHub.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final List<String> EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");
    private final String USER_DIR = "C:/Users/lucam/Drive/Desktop/EventHub/Storage/Images/User/";
    private final String EVENT_DIR = "C:/Users/lucam/Drive/Desktop/EventHub/Storage/Images/Event/";

    @GetMapping("/event/{id}")
    public ResponseEntity<Resource> getEventImage(@PathVariable String id) {
        for (String ext : EXTENSIONS) {
            Path imagePath = Paths.get(EVENT_DIR + id + "." + ext);
            File imageFile = imagePath.toFile();
            if (imageFile.exists()) {
                try {
                    Resource resource = new UrlResource(imagePath.toUri());
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, "image/" + ext)
                            .body(resource);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Resource> getUserImage(@PathVariable String id) {
        for (String ext : EXTENSIONS) {
            Path imagePath = Paths.get(USER_DIR + id + "." + ext);
            File imageFile = imagePath.toFile();
            if (imageFile.exists()) {
                try {
                    Resource resource = new UrlResource(imagePath.toUri());
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, "image/" + ext)
                            .body(resource);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/remove/{id}")
    public ResponseEntity<String> removeProfileImage(@PathVariable String id) {
        boolean removedFile = deleteExistingImages(id);
        if (removedFile) {
            return new ResponseEntity<>("Profile image successfully removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Profile image not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/event/{id}")
    public ResponseEntity<String> uploadEventImage(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Il file è vuoto.");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return ResponseEntity.badRequest().body("Nome del file non valido.");
        }
        String fileExtension = getFileExtension(originalFilename);
        if (!EXTENSIONS.contains(fileExtension)) {
            return ResponseEntity.badRequest().body("Estensione del file non supportata.");
        }
        Path imagePath = Paths.get(EVENT_DIR + id + "." + fileExtension);
        deleteExistingImages(id);

        try {
            // Salva il file nel percorso specificato
            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok("File salvato con successo: " + id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il salvataggio del file: " + e.getMessage());
        }
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<String> uploadUserImage(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Il file è vuoto.");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return ResponseEntity.badRequest().body("Nome del file non valido.");
        }
        String fileExtension = getFileExtension(originalFilename);
        if (!EXTENSIONS.contains(fileExtension)) {
            return ResponseEntity.badRequest().body("Estensione del file non supportata.");
        }
        Path imagePath = Paths.get(USER_DIR + id + "." + fileExtension);
        deleteExistingImages(id);

        try {
            // Salva il file nel percorso specificato
            Files.copy(file.getInputStream(), imagePath);
            return ResponseEntity.ok("File salvato con successo: " + id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il salvataggio del file: " + e.getMessage());
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private boolean deleteExistingImages(String id) {
        boolean removedFile = false;
        for (String ext : EXTENSIONS) {
            Path imagePath = Paths.get(USER_DIR + id + "." + ext);
            File imageFile = imagePath.toFile();
            if (imageFile.exists()) {
                imageFile.delete();
                removedFile = true;
            }
        }
        return removedFile;
    }

}
