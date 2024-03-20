package com.scrum.docuproject.service;//package com.scrum.docuproject.service;
//
//import com.scrum.docuproject.models.FileDocument;
//import com.scrum.docuproject.models.Versions;
//import com.scrum.docuproject.repository.VersionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class VersionServiceImpl implements VersionService{
//    @Autowired
//    VersionRepository versionRepository;
//    @Autowired
//    DocumentService documentService;
//
//    @Override
//    public List<Versions> getAllVersion(){
//        return versionRepository.findAll();
//    }
//
//    @Override
//    public List<Versions> getVersionByDocId(FileDocument fileDocument){
//        String id = fileDocument.getId();
//        return versionRepository.findAllById(id);
//    }
//    @Override
//    public void addVersion(Versions versions){
//        versionRepository.save(versions);
//    }
//
//    @Override
//    public void deleteVer(Versions versions){
//        versionRepository.deleteById(versions.getId());
//    }
//
//    @Override
//    public void deleteAll(Versions versions){
//        versionRepository.deleteAll();
//    }
//
//
//
//}
