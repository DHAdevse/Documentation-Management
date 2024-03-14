package com.scrum.docuproject.service;

import com.scrum.docuproject.models.FileDocument;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface DocumentService {

    public Optional<FileDocument> findDocument(String id);
    public FileDocument addDocument(FileDocument document);
    public List<FileDocument> getAll();
    public void deteleById(String id);

    public FileDocument updateDocument(FileDocument fileDocument);
}
