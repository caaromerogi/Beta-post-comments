package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.handlers;


import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository.MongoViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases.BringAllPostsUseCase;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases.BringPostById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QueryHandler {
    @Autowired
    MongoViewRepository repository;

    @Bean
    public RouterFunction<ServerResponse> findPostById(BringPostById bringPostById){
        return route(GET("/get/post/{id}"),request -> {
            Mono<PostViewModel> postViewModel = bringPostById.apply(request.pathVariable("id"));
            return postViewModel.hasElement().flatMap(b-> {
                if(b){
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromPublisher(postViewModel, PostViewModel.class));                }
                return ServerResponse.notFound().build();
            });

        });
    }

    @Bean
    public RouterFunction<ServerResponse> findAllPosts(BringAllPostsUseCase bringAllPostsUseCase){
        return route(GET("/get/AllPosts"), request -> {
           return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                   .body(bringAllPostsUseCase.get(),PostViewModel.class);
        });
    }
    //Create a route that allows you to make a Get Http request that brings you all the posts and also a post by its id
}
