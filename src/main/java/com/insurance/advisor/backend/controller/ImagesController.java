package com.insurance.advisor.backend.controller;

import com.insurance.advisor.backend.model.Images;
import com.insurance.advisor.backend.repository.ImagesRepository;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/images")
public class ImagesController {

    @Autowired
    private ImageKit imageKit; // Injected from ImageKitConfig

    @Autowired
    private ImagesRepository imagesRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Prepare the request for ImageKit
            FileCreateRequest fileCreateRequest = new FileCreateRequest(file.getBytes(), file.getOriginalFilename());
            fileCreateRequest.setUseUniqueFileName(true);
            fileCreateRequest.setFolder("/insurance_assets");

            // 2. Upload to Cloud
            Result result = imageKit.upload(fileCreateRequest);

            // 3. Map result to your PostgreSQL Entity
            Images Images = new Images();
            Images.setUrl(result.getUrl());
            Images.setFileId(result.getFileId()); // Save this for deletion later!
            Images.setAltText("Insurance Blog Asset");

            // 4. Save to Database
            Images savedRecord = imagesRepository.save(Images);

            return ResponseEntity.ok(savedRecord);

        } catch (Exception e) {
            // Log the error for debugging on your Xubuntu terminal
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Images>> getAllImages() {
        // Fetches all rows from the 'images' table in PostgreSQL
        return ResponseEntity.ok(imagesRepository.findAll());
    }
}