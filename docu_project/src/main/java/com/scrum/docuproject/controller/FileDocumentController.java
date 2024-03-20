package com.scrum.docuproject.controller;

import com.scrum.docuproject.models.FileDocument;
import com.scrum.docuproject.models.Versions;
import com.scrum.docuproject.repository.DocumentRepository;
import com.scrum.docuproject.repository.UserRepository;
import com.scrum.docuproject.service.DocumentService;
import com.scrum.docuproject.service.FirebaseService;
import com.scrum.docuproject.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public FileDocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/create")
    public ResponseEntity<FileDocument> createDocument(@RequestBody FileDocument fileDocument)
    {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        fileDocument.setUser(userRepository.findUserById(userDetails.getId()));
        fileDocument.setVersions(new ArrayList<>());
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

    @PostMapping("/{id}/version/add-file")
    public ResponseEntity<Versions> addFile(@PathVariable String id, @RequestParam("file") MultipartFile file) throws Exception{
        Optional<FileDocument> fileDocument = documentRepository.findById(id);
        Versions versions = new Versions();
        List<Versions> versionsList = fileDocument.get().getVersions();
//        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        String extension = file.getOriginalFilename();
        versions.setNameVer(extension);
        versions.setId(String.valueOf(versionsList.size() + 1));
        versions.setLink(uploadService.uploadFile(file));
        LocalDateTime time = LocalDateTime.now();
        String formatTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        versions.setDate(formatTime);
        versions.setMessage("");
        if(versionsList.isEmpty()){
            versionsList = new ArrayList<>();
        }
        versionsList.add(versions);
        fileDocument.get().setVersions(versionsList);
        documentRepository.save(fileDocument.get());
        return ResponseEntity.ok(versions);

    }


}
