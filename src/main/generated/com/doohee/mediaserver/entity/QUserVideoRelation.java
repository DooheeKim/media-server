package com.doohee.mediaserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserVideoRelation is a Querydsl query type for UserVideoRelation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserVideoRelation extends EntityPathBase<UserVideoRelation> {

    private static final long serialVersionUID = -1994493051L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserVideoRelation userVideoRelation = new QUserVideoRelation("userVideoRelation");

    public final QUser user;

    public final QVideo video;

    public QUserVideoRelation(String variable) {
        this(UserVideoRelation.class, forVariable(variable), INITS);
    }

    public QUserVideoRelation(Path<? extends UserVideoRelation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserVideoRelation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserVideoRelation(PathMetadata metadata, PathInits inits) {
        this(UserVideoRelation.class, metadata, inits);
    }

    public QUserVideoRelation(Class<? extends UserVideoRelation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
        this.video = inits.isInitialized("video") ? new QVideo(forProperty("video"), inits.get("video")) : null;
    }

}

