package com.scrum.docuproject.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.scrum.docuproject.models.FileDocument;
import com.scrum.docuproject.models.User;
import com.scrum.docuproject.models.Versions;
import com.scrum.docuproject.repository.DocumentRepository;
import com.scrum.docuproject.repository.UserRepository;
import com.scrum.docuproject.repository.VersionRepository;
import com.scrum.docuproject.service.DocumentService;
import com.scrum.docuproject.service.DocumentServiceImpl;
import com.scrum.docuproject.service.FirebaseService;
//import com.scrum.docuproject.service.VersionService;
import com.scrum.docuproject.service.UserDetailsImpl;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/document")
public class FileDocumentController {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private FirebaseService uploadService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VersionRepository versionRepository;
    @Autowired
    public FileDocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/create/{title}")
    public ResponseEntity<FileDocument> createDocument(@PathVariable String title, @RequestParam("file") MultipartFile file) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FileDocument fileDocument = new FileDocument();
        fileDocument.setUser(userRepository.findUserById(userDetails.getId()));
        fileDocument.setTitle(title);

        String originalFileName = file.getOriginalFilename();
        String modifiedFileName = originalFileName;
        Versions versions = new Versions();

        versions.setNameVer(modifiedFileName);
        uploadService.urlFirebase(file);
        versions.setLink(uploadService.uploadFile(file));
        LocalDateTime time = LocalDateTime.now();
        String formatTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        versions.setDate(formatTime);
        versions.setMessage("Initial");
        List<Versions> versionsList = new ArrayList<>();
        versionsList.add(versions);
        fileDocument.setVersions(versionsList);
        versionRepository.save(versions);
        documentRepository.save(fileDocument);
        return ResponseEntity.ok().body(fileDocument);
    }

    @GetMapping("/view-all")
    public ResponseEntity<List<FileDocument>> getAlL()
    {

        return ResponseEntity.ok().body(documentService.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id)
    {
        documentService.deteleById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FileDocument> updateDocument(@PathVariable String id, @RequestBody FileDocument fileDocument)
    {
        Optional<FileDocument> checkDocument = documentService.findDocument(id);
        if(checkDocument.isPresent())
        {
            FileDocument existDocument = checkDocument.get();
            existDocument.setId(id);
            existDocument.setTitle(fileDocument.getTitle());
            existDocument.setSize(fileDocument.getSize());
            documentService.updateDocument(existDocument);
            return ResponseEntity.ok().body(existDocument);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/version/add-file/{message}")
    public ResponseEntity<Versions> addFile(@PathVariable String id, @PathVariable String message, @RequestParam("file") MultipartFile file) throws Exception{
        Optional<FileDocument> findFileDocument = documentRepository.findById(id);
        FileDocument fileDocument = findFileDocument.orElse(null);
        Versions versions = new Versions();
        List<Versions> versionsList = fileDocument.getVersions();
        String originalFileName = file.getOriginalFilename();
        String modifiedFileName = originalFileName;

        for (Versions ver : versionsList){
            String temp = ver.getNameVer();
            if (temp.equals(originalFileName)){

                String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
                modifiedFileName = baseName + "_" + UUID.randomUUID().toString() + extension;
                break;

            }
            else{
//                versions.setId(versionsList);
                versions.setNameVer(modifiedFileName);
                uploadService.urlFirebase(file);
                versions.setLink(uploadService.uploadFile(file));
                LocalDateTime time = LocalDateTime.now();
                String formatTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                versions.setDate(formatTime);
                versions.setMessage(message);
            }
        }


        if(versionsList.isEmpty()){
            versionsList = new ArrayList<>();
        }

        versionsList.add(versions);
        versionRepository.save(versions);
        fileDocument.setVersions(versionsList);
        documentRepository.save(fileDocument);

        return ResponseEntity.ok(versions);

    }


}
