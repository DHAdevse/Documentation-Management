package com.scrum.docuproject.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseService {

    public FirebaseService() throws IOException {
        File serviceAccount = new ClassPathResource("keyFirebase.json").getFile();
        FileInputStream serviceAccountStream = new FileInputStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .setStorageBucket("my-project-scrum-17425.appspot.com")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public String uploadFile(MultipartFile multipartFile) throws Exception {
        String fileName = multipartFile.getOriginalFilename();
//        StorageClient storageClient = StorageClient.getInstance();
//        String fileUrl = storageClient.bucket().create(fileName, multipartFile.getInputStream(), multipartFile.getContentType()).getMediaLink();
        Bucket bucket = StorageClient.getInstance().bucket();
        Storage storage = bucket.getStorage();
        BlobId blobId = BlobId.of(bucket.getName(), fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        URL url = storage.signUrl(blobInfo,7, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature());
        String fileUrl = url.toString();
        return fileUrl;

    }

}
