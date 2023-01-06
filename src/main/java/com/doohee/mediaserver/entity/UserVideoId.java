package com.doohee.mediaserver.entity;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
public class UserVideoId implements Serializable {
    private String user;
    private String video;
}
