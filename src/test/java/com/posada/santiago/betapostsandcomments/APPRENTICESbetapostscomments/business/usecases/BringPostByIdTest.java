package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BringPostByIdTest {
    @Mock
    DomainViewRepository repository;

    BringPostById useCase;

    @BeforeEach
    void init(){
        this.useCase = new BringPostById(repository);
    }

    @Test
    public void BringPostByIdTest() {
        PostViewModel post = new PostViewModel();
        post.setAggregateId("4485415vds");

        Mockito.when(repository.findByAggregateId(Mockito.any(String.class))).thenReturn(Mono.just(post));

        var use = useCase.apply("4485415vds");

        StepVerifier.create(use)
                .expectNext(post)
                .verifyComplete();

        Mockito.verify(repository).findByAggregateId("4485415vds");

    }

}