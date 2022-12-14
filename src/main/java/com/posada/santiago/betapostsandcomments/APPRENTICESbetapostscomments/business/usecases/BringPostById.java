package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;



import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository.MongoViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class BringPostById implements Function<String, Mono<PostViewModel>> {

    private final static Logger logger= LoggerFactory.getLogger(BringPostById.class);
    @Autowired
    DomainViewRepository repository;
    @Override
    public Mono<PostViewModel> apply(String id) {
        logger.info("Bringing a post in beta usecase");
        return repository.findByAggregateId(id);
    }
    // finish the implementation of this class using the functional interfaces
}
