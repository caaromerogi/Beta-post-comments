package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.handlers;



import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.bus.Notification;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases.UpdateViewUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class QueueHandler implements Consumer<String> {
    private final static Logger logger= LoggerFactory.getLogger(QueueHandler.class);
    private final Gson gson = new Gson();
    private final UpdateViewUseCase useCase;

    public QueueHandler(UpdateViewUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void accept(String received) {
        Notification notification = Notification.from(received);
        String type = notification.getType().replace("alphapostsandcomments", "betapostsandcomments.APPRENTICESbetapostscomments");

        try {
           DomainEvent event = (DomainEvent) gson.fromJson(notification.getBody(), Class.forName(type));
           logger.info("Domain event message received in beta from alpha");
            useCase.accept(event);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
