package com.example.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
@Data
public class Post {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments; 
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Like> likes;

    private @Id @GeneratedValue Long postId;
    private String content;
    private String username;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false) 
    private LocalDateTime timestamp;

    public Post() {

    }
    
   
   public Post( String content, String username) {
    this.content = content;
    this.username = username;
    this.timestamp = LocalDateTime.now(); 
    this.comments = new ArrayList<Comments>();
  }



}
