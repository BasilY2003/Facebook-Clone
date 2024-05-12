package com.example.demo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentsAssembler implements RepresentationModelAssembler<Comments, EntityModel<Comments>> {

    @Override
    public EntityModel<Comments> toModel(Comments comment) {

        long commentId = comment.getCommentId();


        return EntityModel.of(comment,
                linkTo(methodOn(CommentsController.class).getComment(comment.getPostid(),comment.getCommentId())).withRel("comment-" + commentId),
                linkTo(methodOn(CommentsController.class).getAllComments(comment.getPostid())).withRel("allComments")
        );
    }
}



