package com.doohee.mediaserver.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column
    private String title;

    @Column(length=4095)
    private String description;

    @Column
    private VideoStatus status;

    @Column
    private Exposure exposure;

    @Column
    private LocalDateTime uploadedDate;

    @Column
    private String extension;
    //media 파일의 확장자''''

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User uploader;

    @OneToMany(mappedBy = "video")
    private List<UserVideoMap> userVideoMaps;

}
