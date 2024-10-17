package com.seshwoods.client;

import com.seshwoods.dto.NotifcationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationService") // -> naam van de service
public interface NotificationClient {

    @PostMapping("/api/notification")
    void sendNotification(@RequestBody NotifcationRequest notifictionRequest);
}

