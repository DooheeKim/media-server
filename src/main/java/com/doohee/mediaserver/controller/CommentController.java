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
        String userId = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.writeComment(userId, inDto.getVideoId(), inDto.getContent(), inDto.getParentCommentId()));
    }
    @GetMapping("/list")
    ResponseEntity<Page<CommentAbstractDto>> listAllComments(@RequestParam String videoId, @RequestParam Integer page){
        String userId = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.getCommentsList(userId, videoId, page, commentsPerPage));
    }
    @PutMapping("/")
    ResponseEntity<CommentAbstractDto> modifyComment(@RequestBody CommentModifyDto inDto){
        String userId = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.modifyComment(userId, inDto.getCommentId(), inDto.getContent()));
    }
    @DeleteMapping("/{commentId}")
    ResponseEntity<CommentAbstractDto> deleteComment(@PathVariable String commentId){
        String userId = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(commentService.deleteComment(userId, commentId));
    }


}
