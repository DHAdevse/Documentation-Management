package com.scrum.docuproject.service;

import com.scrum.docuproject.models.FileDocument;
import com.scrum.docuproject.models.Versions;

import java.util.List;
import java.util.Optional;

public interface DocumentService {

    public Optional<FileDocument> findDocument(String id);
    public Optional<FileDocument> addDocument(Optional<FileDocument> document);
    public List<FileDocument> getAll();
    Optional<FileDocument> getDocById(String id);
    public void deteleById(String id);
    public FileDocument updateDocument(FileDocument fileDocument);
//    public void addFile(Optional <FileDocument>fileDocument, Versions versions);
    public Optional <FileDocument> addFile(Optional <FileDocument>fileDocument, Versions versions);

}
