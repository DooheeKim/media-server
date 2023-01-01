package com.doohee.mediaserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVideoKey is a Querydsl query type for VideoKey
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideoKey extends EntityPathBase<VideoKey> {

    private static final long serialVersionUID = 628001227L;

    public static final QVideoKey videoKey = new QVideoKey("videoKey");

    public final StringPath keyAudio = createString("keyAudio");

    public final StringPath keyFhd = createString("keyFhd");

    public final StringPath keyHd = createString("keyHd");

    public final StringPath keyIdAudio = createString("keyIdAudio");

    public final StringPath keyIdFhd = createString("keyIdFhd");

    public final StringPath keyIdHd = createString("keyIdHd");

    public final StringPath keyIdNhd = createString("keyIdNhd");

    public final StringPath keyIdSd = createString("keyIdSd");

    public final StringPath keyNhd = createString("keyNhd");

    public final StringPath keySd = createString("keySd");

    public final StringPath videoId = createString("videoId");

    public QVideoKey(String variable) {
        super(VideoKey.class, forVariable(variable));
    }

    public QVideoKey(Path<? extends VideoKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVideoKey(PathMetadata metadata) {
        super(VideoKey.class, metadata);
    }

}

