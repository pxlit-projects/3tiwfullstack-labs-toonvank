package com.seshwoods.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification{
    private String message;
    private String sender;
}