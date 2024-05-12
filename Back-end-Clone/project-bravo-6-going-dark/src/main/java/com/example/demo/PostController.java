package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    @Autowired
   static PostRepository repository;
    private final PostAssembler postAssembler;

    public PostController(PostRepository repo, PostAssembler postAssembler) {
        PostController.repository = repo;
        this.postAssembler = postAssembler;
    }

   @GetMapping("/posts")
   @CrossOrigin(origins = "http://localhost:5173")
    CollectionModel<EntityModel<Post>> all() {
        List<EntityModel<Post>> posts = repository.findAll().stream()
                .map(postAssembler::toModel)
                .collect(Collectors.toList());

        Link link = linkTo(methodOn(PostController.class).all()).withSelfRel();

        return CollectionModel.of(posts, link);
    }



    @GetMapping("/posts/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
public ResponseEntity<EntityModel<Post>> one(@PathVariable long id) {
    Optional<Post> postOptional = repository.findById(id);

    if (postOptional.isPresent()) {
        Post post = postOptional.get();
        EntityModel<Post> postModel = postAssembler.toModel(post);
        
        return ResponseEntity.ok(postModel);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    
    @PostMapping("/posts")
    @CrossOrigin(origins = "http://localhost:5173")
    public Post AddPost(@RequestBody Post newpost) {
        return repository.save(newpost);
    }

    @DeleteMapping("/posts/{postId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        repository.deleteById(postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {
        Optional<Post> optionalPost = repository.findById(postId);

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setContent(updatedPost.getContent());
            repository.save(existingPost);

            return ResponseEntity.ok(existingPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    

    

}
