package com.seshwoods.service;

import com.seshwoods.model.Notification;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;


@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void sendMessage(Notification notification){
        log.info("Recieving notifcation...");
        log.info("Sending... {}", notification.getMessage());
        log.info("To {}",notification.getSender());
    }
}
