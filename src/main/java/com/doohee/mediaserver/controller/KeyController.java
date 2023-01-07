package com.doohee.mediaserver.controller;

import com.doohee.mediaserver.service.KeyService;
import com.doohee.mediaserver.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/keys")
public class KeyController {
    @Autowired
    KeyService keyService;

    @GetMapping("/{videoId}")
    public ResponseEntity<Map<String, String>> getVideoKey(@PathVariable String videoId){
        String username = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(keyService.getKey(username, videoId));
    }
}
