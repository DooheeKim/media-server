package com.doohee.mediaserver;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class GreetingController {
    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    @GetMapping("/dash/{filename}.mpd")
    public ResponseEntity<Resource> getMpdFile(@PathVariable String filename){
        String fullPath="/Users/doohee/Documents/ffmpeg-videos/convert/"+filename+".mpd";
        Resource resource = new FileSystemResource(fullPath);
        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
    }
    @GetMapping("/dash/{filename}.m4s")
    public ResponseEntity<Resource> getVideoSegmentFile(@PathVariable String filename){
        String fullPath="/Users/doohee/Documents/ffmpeg-videos/convert/"+filename+".m4s";
        Resource resource = new FileSystemResource(fullPath);
        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
    }
}
