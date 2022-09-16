package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository.MongoViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.handlers.QueueHandler;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class BringAllPostsUseCase implements Supplier<Flux<PostViewModel>>{

    private final static Logger logger= LoggerFactory.getLogger(BringAllPostsUseCase.class);
    @Autowired
    private DomainViewRepository repository;

    @Override
    public Flux<PostViewModel> get() {
        logger.info("Finding all posts in beta useCase");
        return repository.findAllPosts();
    }


    //Finish the implementation of this class using the functional interfaces
}
