package com.seshwoods.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    //no database needed for notifications because they will just be sent not saved
}