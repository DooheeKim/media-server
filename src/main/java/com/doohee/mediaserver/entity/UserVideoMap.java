package com.doohee.mediaserver.entity;

import javax.persistence.*;

@Entity
@IdClass(UserVideoId.class)
public class UserVideoMap {
    @Id
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="VIDEO_ID")
    private Video video;
}
