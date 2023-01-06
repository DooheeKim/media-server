package com.doohee.mediaserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideo is a Querydsl query type for Video
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideo extends EntityPathBase<Video> {

    private static final long serialVersionUID = 1396451316L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideo video = new QVideo("video");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final EnumPath<Exposure> exposure = createEnum("exposure", Exposure.class);

    public final StringPath extension = createString("extension");

    public final EnumPath<VideoStatus> status = createEnum("status", VideoStatus.class);

    public final StringPath thumbnailExtension = createString("thumbnailExtension");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> uploadedDate = createDateTime("uploadedDate", java.time.LocalDateTime.class);

    public final QUser uploader;

    public final ListPath<UserVideoRelation, QUserVideoRelation> userVideoRelations = this.<UserVideoRelation, QUserVideoRelation>createList("userVideoRelations", UserVideoRelation.class, QUserVideoRelation.class, PathInits.DIRECT2);

    public final StringPath videoId = createString("videoId");

    public QVideo(String variable) {
        this(Video.class, forVariable(variable), INITS);
    }

    public QVideo(Path<? extends Video> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideo(PathMetadata metadata, PathInits inits) {
        this(Video.class, metadata, inits);
    }

    public QVideo(Class<? extends Video> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.uploader = inits.isInitialized("uploader") ? new QUser(forProperty("uploader")) : null;
    }

}

