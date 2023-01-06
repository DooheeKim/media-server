package com.doohee.mediaserver.dto;

import com.doohee.mediaserver.entity.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentAbstractDto {
    String commentId;
    String content;
    String writerId;
    String writerNickname;
    LocalDateTime writtenDate;
    boolean fixed;

    int numChildComments;

    @QueryProjection
    public CommentAbstractDto(String commentId, String content, String writerId, String writerNickname, LocalDateTime writtenDate, boolean fixed, int numChildComments){
        this.commentId = commentId;
        this.content = content;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writtenDate = writtenDate;
        this.fixed = fixed;
        this.numChildComments = numChildComments;
    }

    @QueryProjection
    public CommentAbstractDto(String commentId, String content, String writerId, String writerNickname, LocalDateTime writtenDate, boolean fixed){
        this.commentId = commentId;
        this.content = content;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writtenDate = writtenDate;
        this.fixed = fixed;
    }
    public CommentAbstractDto(String commentId){
        this.commentId = commentId;
    }

    public static CommentAbstractDto from(Comment comment){
        CommentAbstractDto ret = CommentAbstractDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .writerId(comment.getWriter().getUsername())
                .writerNickname(comment.getWriter().getNickname())
                .writtenDate(comment.getRegisterDate())
                .fixed(comment.isFixed())
                .build();
        return ret;
    }
}
