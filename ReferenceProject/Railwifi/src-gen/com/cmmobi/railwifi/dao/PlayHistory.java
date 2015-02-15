package com.cmmobi.railwifi.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PLAY_HISTORY.
 */
public class PlayHistory {

    private String media_id;
    private int media_type;
    private String name;
    private String img_path;
    private String src_url;
    private String location;
    private String percent;
    private String totaltime;

    public PlayHistory() {
    }

    public PlayHistory(String media_id) {
        this.media_id = media_id;
    }

    public PlayHistory(String media_id, int media_type, String name, String img_path, String src_url, String location, String percent, String totaltime) {
        this.media_id = media_id;
        this.media_type = media_type;
        this.name = name;
        this.img_path = img_path;
        this.src_url = src_url;
        this.location = location;
        this.percent = percent;
        this.totaltime = totaltime;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getSrc_url() {
        return src_url;
    }

    public void setSrc_url(String src_url) {
        this.src_url = src_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

}
