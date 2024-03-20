package com.scrum.docuproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Document("versions")
public class Versions {

    @Id
    private String id;
    private String nameVer;
    @DBRef
    private FileDocument fileDocument;
    private String link;
    private String date;
    private  String message;
}
