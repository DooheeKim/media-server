package com.doohee.mediaserver.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.doohee.mediaserver.dto.QVideoAbstractDto is a Querydsl Projection type for VideoAbstractDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QVideoAbstractDto extends ConstructorExpression<VideoAbstractDto> {

    private static final long serialVersionUID = -1237607275L;

    public QVideoAbstractDto(com.querydsl.core.types.Expression<String> videoId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> thumbnailExtension, com.querydsl.core.types.Expression<String> uploader, com.querydsl.core.types.Expression<java.time.LocalDateTime> uploadedDate) {
        super(VideoAbstractDto.class, new Class<?>[]{String.class, String.class, String.class, String.class, java.time.LocalDateTime.class}, videoId, title, thumbnailExtension, uploader, uploadedDate);
    }

}

