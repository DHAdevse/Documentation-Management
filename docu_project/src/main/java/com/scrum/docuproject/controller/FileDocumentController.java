package com.scrum.docuproject.controller;

import com.scrum.docuproject.models.FileDocument;
import com.scrum.docuproject.service.DocumentService;
import com.scrum.docuproject.service.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/main")
public class FileDocumentController {
    private DocumentService documentService;

    @Autowired
    public FileDocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/create")
    public ResponseEntity<FileDocument> createDocument(@RequestBody FileDocument fileDocument)
    {
        documentService.addDocument(fileDocument);
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
}
