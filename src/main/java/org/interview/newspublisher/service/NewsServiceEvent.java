package org.interview.newspublisher.service;

import org.springframework.context.ApplicationEvent;

/**
 * This is an optional class used in publishing application events.
 * This can be used to inject events into the Spring Boot audit management endpoint.
 */
public class NewsServiceEvent extends ApplicationEvent {

    public NewsServiceEvent(Object source) {
        super(source);
    }
    
    @Override
    public String toString() {
        return "My NewsService Event";
    }
}