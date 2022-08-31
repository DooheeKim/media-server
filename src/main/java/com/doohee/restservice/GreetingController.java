package com.doohee.restservice;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;
/*
*@GetMapping("/hls/{fileName}/{fileName}.m3u8")
	public ResponseEntity<Resource> videoHlsM3U8(@PathVariable String fileName) {
		log.debug("************** class = {}, function = {}", this.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
		String fileFullPath = UPLOAD_DIR + fileName + "/" + fileName + ".m3u8";
		Resource resource = new FileSystemResource(fileFullPath);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".m3u8");
		headers.setContentType(MediaType.parseMediaType("application/vnd.apple.mpegurl"));
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}

	@GetMapping("/hls/{fileName}/{tsName}.ts")
	public ResponseEntity<Resource> videoHlsTs(@PathVariable String fileName, @PathVariable String tsName) {
		log.debug("************** class = {}, function = {}", this.getClass().getName(), new Object(){}.getClass().getEnclosingMethod().getName());
		String fileFullPath = UPLOAD_DIR + fileName + "/" + tsName + ".ts";
		Resource resource = new FileSystemResource(fileFullPath);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tsName + ".ts");
		headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE));
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
출처: https://suyou.tistory.com/265 [수유산장:티스토리]
* */
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
