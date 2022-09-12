package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class UpdateViewUseCase implements Consumer<DomainEvent>{
//    private BusRepository bus;
    private ViewUpdater viewUpdater;

    public UpdateViewUseCase(ViewUpdater viewUpdater) {
//        this.bus = bus;
        this.viewUpdater = viewUpdater;
    }
    @Override
    public void accept(DomainEvent domainEvent){
//        bus.publish(domainEvent);
        viewUpdater.applyEvent(domainEvent);
    }
}
