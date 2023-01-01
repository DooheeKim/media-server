package com.doohee.mediaserver.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.doohee.mediaserver.dto.QCommentAbstractDto is a Querydsl Projection type for CommentAbstractDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentAbstractDto extends ConstructorExpression<CommentAbstractDto> {

    private static final long serialVersionUID = -944649359L;

    public QCommentAbstractDto(com.querydsl.core.types.Expression<String> commentId, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> writerId, com.querydsl.core.types.Expression<String> writerNickname, com.querydsl.core.types.Expression<java.time.LocalDateTime> writtenDate, com.querydsl.core.types.Expression<Boolean> fixed) {
        super(CommentAbstractDto.class, new Class<?>[]{String.class, String.class, String.class, String.class, java.time.LocalDateTime.class, boolean.class}, commentId, content, writerId, writerNickname, writtenDate, fixed);
    }

}

