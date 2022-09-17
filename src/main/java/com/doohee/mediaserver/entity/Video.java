package com.doohee.mediaserver.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name="VIDEO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    @Column(name="VIDEO_ID", length = 127)
    private String videoId;

    @Column(length=63)
    private String status;

    @Column
    private Exposure exposure;

    @Column
    private Date uploadedDate;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User uploader;

    @OneToMany(mappedBy = "video")
    private List<UserVideoMap> userVideoMaps;

}
