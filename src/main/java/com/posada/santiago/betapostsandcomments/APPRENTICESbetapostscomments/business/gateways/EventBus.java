package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;

public interface EventBus {
    void publishPostViewModel(PostViewModel postViewModel);

    void publishAddComment(CommentViewModel commentViewModel);


    void publishError(Throwable errorEvent);
}
