package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "likes")
public class Like {
    
@Id @GeneratedValue private Long id;

private String AuthorName;

public Like(String auth,long id){
    this.AuthorName=auth;
    this.id = id;
}

public Like(){

}

}
