package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CommentsController {

    @Autowired
    PostRepository repository;
    CommentsAssembler commentassembler;

    public CommentsController(PostRepository repository,CommentsAssembler assembler) {
        this.repository = repository;
        this.commentassembler = assembler;
    }

    @PostMapping("/posts/{id}/comments")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Post> addComment(@RequestBody Comments entity, @PathVariable long id) {
        Optional<Post> postOptional = repository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.getComments().add(entity);
            repository.save(post);
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // @GetMapping("/comment-likes/{commId}")
    // public List<Like> getMethodName(@RequestParam String param) {
    //     return ;
    // }
    


   @GetMapping("/posts/{postId}/comments")
   @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<EntityModel<Comments>>> getAllComments(@PathVariable long postId) {
        Optional<Post> postOptional = repository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            List<EntityModel<Comments>> commentModels = post.getComments().stream()
            .map(commentassembler::toModel)  
            .collect(Collectors.toList());
            return ResponseEntity.ok(commentModels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @CrossOrigin(origins = "http://localhost:5173")
public ResponseEntity<Post> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
    Optional<Post> postOptional = repository.findById(postId);

    if (postOptional.isPresent()) {
        Post post = postOptional.get();
        post.getComments().removeIf(comment -> comment.getCommentId() == commentId);
        repository.save(post);
        return ResponseEntity.ok(post);
    } else {
        return ResponseEntity.notFound().build();
    }
}

@GetMapping("/posts/{postId}/comments/{commentId}")
@CrossOrigin(origins = "http://localhost:5173")
public ResponseEntity<EntityModel<Comments>> getComment(@PathVariable long postId, @PathVariable long commentId) {
    Optional<Post> postOptional = repository.findById(postId);

    if (postOptional.isPresent()) {
        Post post = postOptional.get();
        Optional<Comments> commentOptional = post.getComments().stream()
                .filter(comment -> comment.getCommentId() == commentId)
                .findFirst();

        if (commentOptional.isPresent()) {
            Comments comment = commentOptional.get();
            EntityModel<Comments> commentModel = commentassembler.toModel(comment);
            return ResponseEntity.ok(commentModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    } else {
        return ResponseEntity.notFound().build();
    }
}



}
