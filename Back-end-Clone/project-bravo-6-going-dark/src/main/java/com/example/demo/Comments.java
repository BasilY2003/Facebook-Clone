package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Comments {

    private  @Id @GeneratedValue Long commentId;
    private String userName;
    private long postid;


      @OneToMany(cascade = CascadeType.ALL)
     private List<Like> likes;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false) 
    private LocalDateTime date;
    private String content;
    

    
    public Comments(){}

    public Comments( String userName, String content,long postid) {
       
        this.userName = userName;
        this.content = content;
        this.postid = postid;
        this.date = LocalDateTime.now(); 
        
    }

   
}


