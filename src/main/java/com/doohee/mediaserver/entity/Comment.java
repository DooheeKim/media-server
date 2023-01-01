package com.doohee.mediaserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="COMMENT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @Column(name="COMMENT_ID")
    private String commentId;

    @Column(length = 4096)
    private String content;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User writer;

    @Column
    private LocalDateTime registerDate;

    @Column
    private boolean fixed;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COMMENT_ID",insertable=false, updatable=false)
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch=FetchType.LAZY)
    private List<Comment> childComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="VIDEO_ID", insertable = false, updatable = false)
    private Video video;

}
