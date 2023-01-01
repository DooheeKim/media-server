package com.doohee.mediaserver.dto;

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

    @QueryProjection
    public CommentAbstractDto(String commentId, String content, String writerId, String writerNickname, LocalDateTime writtenDate, boolean fixed){
        this.commentId = commentId;
        this.content = content;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writtenDate = writtenDate;
        this.fixed = fixed;
    }
}
