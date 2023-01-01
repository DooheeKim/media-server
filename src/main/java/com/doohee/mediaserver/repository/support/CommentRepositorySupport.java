package com.doohee.mediaserver.repository.support;

import com.doohee.mediaserver.dto.CommentAbstractDto;
import com.doohee.mediaserver.dto.QCommentAbstractDto;
import com.doohee.mediaserver.dto.QVideoAbstractDto;
import com.doohee.mediaserver.dto.VideoAbstractDto;
import com.doohee.mediaserver.entity.Comment;
import com.doohee.mediaserver.entity.Exposure;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.doohee.mediaserver.entity.QVideo.video;
import static com.doohee.mediaserver.entity.QComment.comment;

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    Environment environment;
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public CommentRepositorySupport() {
        super(Comment.class);
    }

    public Page<CommentAbstractDto> findCommentByVideoId(String videoId, Pageable pageable){
        JPAQuery<CommentAbstractDto> query = jpaQueryFactory.select(new QCommentAbstractDto(comment.commentId, comment.content, comment.writer.username, comment.writer.nickname,
                        comment.registerDate, comment.fixed))
                .from(comment)
                .where(videoId(videoId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        query.orderBy(video.uploadedDate.desc());
        List<CommentAbstractDto> results = query.fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

    public Page<CommentAbstractDto> findChildComment(String commentId, Pageable pageable){
        JPAQuery<CommentAbstractDto> query = jpaQueryFactory.select(new QCommentAbstractDto(comment.commentId, comment.content, comment.writer.username, comment.writer.nickname,
                        comment.registerDate, comment.fixed))
                .from(comment)
                .where(parentCommentId(commentId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        query.orderBy(video.uploadedDate.asc());
        List<CommentAbstractDto> results = query.fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

    private BooleanExpression videoId(String videoId){
        return comment.video.videoId.eq(videoId);
    }
    private BooleanExpression parentCommentId(String commentId){
        return comment.parent.commentId.eq(commentId);
    }

}
