package com.scrum.docuproject.controller;


import com.scrum.docuproject.models.Versions;
import com.scrum.docuproject.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class FirebaseController {
    @Autowired
    private  FirebaseService uploadService;

    @PostMapping("/upload")
    public void   uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        try{
            Versions versions = new Versions();
            versions.setLink(uploadService.uploadFile(file));
            LocalDateTime time = LocalDateTime.now();
            String formatTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            versions.setDate(formatTime);
            versions.setMessage("");
        }
        catch (Exception e){
            e.printStackTrace();
        }
//        return uploadService.uploadFile(file);
    }
}
