package com.doohee.key.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name="VIDEO_KEY")
@AllArgsConstructor
@NoArgsConstructor
public class VideoKey {
    @Id
    @Column(name="VIDEO_ID")
    private String videoId;

    //1080p
    @Column(name="KEY_ID_FHD")
    private String keyIdFhd;
    @Column(name="KEY_FHD")
    private String keyFhd;

    //720p
    @Column(name="KEY_ID_HD")
    private String keyIdHd;
    @Column(name="KEY_HD")
    private String keyHd;

    //480p
    @Column(name="KEY_ID_SD")
    private String keyIdSd;
    @Column(name="KEY_SD")
    private String keySd;

    //360p
    @Column(name="KEY_ID_NHD")
    private String keyIdNhd;
    @Column(name="KEY_NHD")
    private String keyNhd;

    //audio
    @Column(name="KEY_ID_AUDIO")
    private String keyIdAudio;
    @Column(name="KEY_AUDIO")
    private String keyAudio;
}
