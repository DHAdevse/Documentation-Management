package com.scrum.docuproject.repository;

import com.scrum.docuproject.models.Versions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionRepository extends MongoRepository<Versions, String> {

}
