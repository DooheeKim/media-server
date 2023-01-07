package com.doohee.mediaserver.controller;

import com.doohee.mediaserver.dto.CommentAbstractDto;
import com.doohee.mediaserver.dto.CommentModifyDto;
import com.doohee.mediaserver.dto.CommentWriteDto;
import com.doohee.mediaserver.service.CommentService;
import com.doohee.mediaserver.util.SecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;
    final int commentsPerPage = 20;

    @PostMapping("/")
    ResponseEntity<CommentAbstractDto> writeComment(@RequestBody CommentWriteDto inDto){
        String username = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.writeComment(username, inDto.getVideoId(), inDto.getContent(), inDto.getParentCommentId()));
    }
    @GetMapping("/list")
    ResponseEntity<Page<CommentAbstractDto>> listAllComments(@RequestParam String videoId, @RequestParam(required = false, defaultValue = "0") Integer page){
        String username = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.getCommentsList(username, videoId, page, commentsPerPage));
    }
    @PutMapping("/")
    ResponseEntity<CommentAbstractDto> modifyComment(@RequestBody CommentModifyDto inDto){
        String username = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.modifyComment(username, inDto.getCommentId(), inDto.getContent()));
    }
    @DeleteMapping("/{commentId}")
    ResponseEntity<CommentAbstractDto> deleteComment(@PathVariable String commentId){
        String username = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.deleteComment(username, commentId));
    }


}
