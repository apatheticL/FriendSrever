package com.hunglephuong.fiendlyserver;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Component
public class FirebaseManager {
    public FirebaseManager() {
        try {
            InputStream serviceAccount = new ClassPathResource("friendly.json").getInputStream();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://minichat-78f54.firebaseio.com")
                    .setStorageBucket("minichat-78f54.appspot.com")
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String fileName = multipartFile.getResource().getFilename();
        fileName = new Date().getTime()+"_"+fileName;
        bucket.create(fileName, multipartFile.getInputStream(), multipartFile.getContentType());
        return fileName;
    }

    public byte[] getImage(String fileName) {
        Bucket bucket = StorageClient.getInstance().bucket();
        return bucket.get(fileName).getContent();
    }
}
