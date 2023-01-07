package com.doohee.mediaserver.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@IdClass(UserVideoId.class)
public class UserVideoRelation {
    @Id
    @ManyToOne
    @JoinColumn(name="USERNAME")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="VIDEO_ID")
    private Video video;
}
