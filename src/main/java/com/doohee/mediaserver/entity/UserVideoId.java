package com.doohee.mediaserver.entity;


import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class UserVideoId implements Serializable {
    private String user;
    private String video;
}
