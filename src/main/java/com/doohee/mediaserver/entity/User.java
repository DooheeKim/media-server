package com.doohee.mediaserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name="USERS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name="USER_ID", length = 127)
    private String userId;

    @JsonIgnore
    @Column(length=128)
    private String password;

    @JsonIgnore
    @Column(length=10)
    private String username;


    @OneToMany(mappedBy="uploader")
    private List<Video> uploadedVideos = new ArrayList<Video>();

    @ManyToMany
    @JoinTable(
            name="user_authority",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name="AUTHORITY_NAME", referencedColumnName = "AUTHORITY_NAME")}
    )
    private Set<Authority> authorities;

}
