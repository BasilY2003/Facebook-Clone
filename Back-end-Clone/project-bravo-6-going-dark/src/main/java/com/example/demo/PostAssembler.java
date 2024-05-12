package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PostAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {

  

    @Override
    public EntityModel<Post> toModel(Post post) {
        try {
            EntityModel<Post> postModel = EntityModel.of(post,
            linkTo(methodOn(CommentsController.class).getAllComments(post.getPostId())).withRel("Comments"),
            linkTo(methodOn(LikesController.class).getLikes(post.getPostId())).withRel("Likes"));
                    

            return postModel;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating HATEOAS links for post", e);
        }
    }
}
