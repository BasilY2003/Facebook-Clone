package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;




@RestController(value = "likes")
@CrossOrigin(origins = "http://localhost:5173")
public class LikesController {
    
PostRepository postrepo;

public LikesController(PostRepository postrepo){
this.postrepo = postrepo;
}


@PostMapping("/post-liked/{postId}")
public ResponseEntity<Like> addLike(@PathVariable Long postId, @RequestBody Like like) {
    Post post = PostController.repository.findById(postId).orElse(null);
    
    if (post == null) {
        return ResponseEntity.notFound().build();
    }
    
    post.getLikes().add(like);
    
    postrepo.save(post);
    
    return ResponseEntity.status(HttpStatus.CREATED).body(like);
}
    

@GetMapping("/all-likes/{id}")
public List<Like> getLikes(@PathVariable Long id) {
    return postrepo.findById(id).get().getLikes();
}



}
