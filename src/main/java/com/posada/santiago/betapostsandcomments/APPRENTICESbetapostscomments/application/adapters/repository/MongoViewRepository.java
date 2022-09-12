package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository;


import com.google.gson.Gson;
import com.mongodb.client.result.UpdateResult;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class MongoViewRepository implements DomainViewRepository {
    private final ReactiveMongoTemplate template;

    private final Gson gson = new Gson();

    public MongoViewRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<PostViewModel> findByAggregateId(String aggregateId) {
        /**Make the implementation, using the template, to find a post by its aggregateId*/
        Query query = new Query(Criteria.where("aggregateId").is(aggregateId));
        return template.findOne(query,PostViewModel.class);
    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        /**make the implementation, using the template, of the method find all posts that are stored in the db*/
        return template.findAll(PostViewModel.class);
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel post) {
        /** make the implementation, using the template, to save a post*/
        return template.save(post);
    }

    @Override
    public Mono<PostViewModel> addCommentToPost(CommentViewModel comment) {
        Query query = new Query(Criteria.where("aggregateId").is(comment.getPostId()));
        Mono<PostViewModel> post =template.findOne(query, PostViewModel.class);
        return post.flatMap(postD -> {
            List<CommentViewModel> comments = postD.getComments();
            comments.add(comment);
            postD.setComments(comments);
            return template.save(postD);
        });
    }
}
