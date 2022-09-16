package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository;


import com.google.gson.Gson;
import com.mongodb.client.result.UpdateResult;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.bus.RabbitMqEventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger= LoggerFactory.getLogger(MongoViewRepository.class);
    private final ReactiveMongoTemplate template;

    private final Gson gson = new Gson();

    public MongoViewRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<PostViewModel> findByAggregateId(String aggregateId) {
        /**Make the implementation, using the template, to find a post by its aggregateId*/
        Query query = new Query(Criteria.where("aggregateId").is(aggregateId));
        logger.info("Executing findByAggregateId query in mongoviewrepository beta");
        return template.findOne(query,PostViewModel.class);
    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        logger.info("Executing findAllPosts query in mongoviewrepository beta");
        /**make the implementation, using the template, of the method find all posts that are stored in the db*/
        return template.findAll(PostViewModel.class);
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel post) {
        logger.info("Executing saveNewPost query in mongoviewrepository beta");
        /** make the implementation, using the template, to save a post*/
        return template.save(post);
    }

    @Override
    public Mono<PostViewModel> addCommentToPost(CommentViewModel comment) {
        logger.info("Executing addCommentToPost query in mongoviewrepository beta");
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
