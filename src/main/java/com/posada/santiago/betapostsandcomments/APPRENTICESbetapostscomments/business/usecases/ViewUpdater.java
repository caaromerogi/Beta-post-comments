package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;


import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.generic.DomainUpdater;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.PostCreated;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ViewUpdater extends DomainUpdater {

    private final DomainViewRepository domainViewRepository;

    public ViewUpdater(DomainViewRepository domainViewRepository) {
        this.domainViewRepository = domainViewRepository;

        listen((PostCreated event) -> {
            PostViewModel post = new PostViewModel(event.aggregateRootId(), event.getAuthor(), event.getTitle(), new ArrayList<>());
            domainViewRepository.saveNewPost(post).subscribe();
        });
        listen((CommentAdded event) -> {
            CommentViewModel comment = new CommentViewModel(event.aggregateRootId(), event.getId(), event.getAuthor(), event.getContent());
            domainViewRepository.addCommentToPost(comment).subscribe();
        });

    }
}
