package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import com.mongodb.client.result.UpdateResult;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateViewUseCaseTest {
    @Mock
    DomainViewRepository repository;

    @Mock
    EventBus eventBus;

    ViewUpdater viewUpdater;

    UpdateViewUseCase useCase;


    @BeforeEach
    void init(){
        this.viewUpdater = new ViewUpdater(repository, eventBus);
        this.useCase = new UpdateViewUseCase(viewUpdater);

    }

    @Test
    public void updateViewUseCaseTest() {
        PostCreated post = new PostCreated("Hola mundo", "Carlos Romero");
        PostViewModel postView = new PostViewModel();
        postView.setAuthor("Carlos Romero");
        postView.setTitle("Hola mundo");

        Mockito.when(repository.saveNewPost(Mockito.any(PostViewModel.class))).thenReturn(Mono.just(postView));
        useCase.accept(post);
        viewUpdater.applyEvent(post);


        Mockito.verify(repository,Mockito.atLeastOnce()).saveNewPost(Mockito.any(PostViewModel.class));
    }

    @Test
    public void updateViewUseCaseTest1() {
        PostViewModel post = new PostViewModel();
        CommentAdded comment = new CommentAdded("vfdsf", "Carlos", "Hola");
        CommentViewModel commentViewModel = new CommentViewModel();
        commentViewModel.setId("vfdsf");
        commentViewModel.setAuthor("Carlos");
        commentViewModel.setContent("Hola");
        List<CommentViewModel> comments = new ArrayList<>();
        comments.add(commentViewModel);

        post.setComments(comments);

        Mockito.when(repository.addCommentToPost(Mockito.any(CommentViewModel.class))).thenReturn(Mono.just(post));
        useCase.accept(comment);
        viewUpdater.applyEvent(comment);


        Mockito.verify(repository,Mockito.atLeastOnce()).addCommentToPost(Mockito.any(CommentViewModel.class));
    }
}