package com.scrum.docuproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("documents")
public class FileDocument {
    @Id
    private String id;
    private String title;
    @DBRef
    private User user;
    @DBRef
    private List<Versions> versions ;
    private int size;

}
