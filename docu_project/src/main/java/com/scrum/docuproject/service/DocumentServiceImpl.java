package com.scrum.docuproject.service;

import com.scrum.docuproject.models.FileDocument;
import com.scrum.docuproject.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService{
    private DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Optional<FileDocument> findDocument(String id) {
        return documentRepository.findById(id);
    }

    @Override
    public FileDocument addDocument(FileDocument document) {
        documentRepository.save(document);
        return document;
    }

    @Override
    public List<FileDocument> getAll() {
        List<FileDocument> fileDocumentList = documentRepository.findAll();
        return fileDocumentList;
    }

    @Override
    public void deteleById(String id) {
        documentRepository.deleteById(id);
    }

    @Override
    public FileDocument updateDocument(FileDocument fileDocument) {
        documentRepository.save(fileDocument);
        return fileDocument;
    }


}
