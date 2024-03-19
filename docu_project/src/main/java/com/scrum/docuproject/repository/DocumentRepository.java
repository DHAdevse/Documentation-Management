package com.scrum.docuproject.repository;

import com.scrum.docuproject.models.FileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends MongoRepository<FileDocument, String> {
}
