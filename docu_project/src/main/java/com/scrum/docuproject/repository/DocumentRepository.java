package com.scrum.docuproject.repository;

import com.scrum.docuproject.models.FileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends MongoRepository<FileDocument, String> {
    Optional<FileDocument> findById(String id);
//    FileDocument save(FileDocument fileDocument);

    void save(Optional<FileDocument> document);
}
