package com.doohee.mediaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentWriteDto {
    String videoId;
    String content;
    String parentCommentId;
}
