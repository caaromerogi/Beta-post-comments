package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BringAllPostsUseCaseTest {

    @Mock
    DomainViewRepository repository;

    BringAllPostsUseCase useCase;

    @BeforeEach
    void init(){
        this.useCase = new BringAllPostsUseCase(repository);
    }

    @Test
    public void bringAllPostsUseCaseTest(){
        PostViewModel post1 = new PostViewModel();
        post1.setAuthor("Carlos Romero");

        PostViewModel post2 = new PostViewModel();

        PostViewModel post3 = new PostViewModel();
        post3.setAuthor("Jose Lopez");
        post3.setTitle("Hola");

        Mockito.when(repository.findAllPosts()).thenReturn(Flux.just(post1,post2,post3));

        var c = useCase.get();

        StepVerifier.create(c)
                .expectNext(post1,post2, post3)
                .verifyComplete();

        Mockito.verify(repository).findAllPosts();
    }

}