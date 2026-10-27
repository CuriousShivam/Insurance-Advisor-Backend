package com.insurance.advisor.backend.feature.image.entity;

import com.insurance.advisor.backend.feature.image.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/images")
public class ImagesController {

    @Autowired
    private ImagesService imagesService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        return imagesService.uploadImage(file);
    }

    @GetMapping
    public ResponseEntity<List<Images>> getAllImages() {
        // Fetches all rows from the 'images' table in PostgreSQL
        return imagesService.getAllImages();
    }
}