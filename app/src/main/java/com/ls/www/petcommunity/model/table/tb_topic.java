package com.ls.www.petcommunity.model.table;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class tb_topic extends BmobObject {

    private String content;
    private String provenance;
    private String author;
    private String topic;
    private BmobFile image;
    private _User userId;
    private String userOnlyId;
    private BmobRelation likesId;
    private Integer likeSum = 0;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public _User getUserId() {
        return userId;
    }

    public void setUserId(_User userId) {
        this.userId = userId;
    }

    public String getUserOnlyId() {
        return userOnlyId;
    }

    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId;
    }

    public BmobRelation getLikesId() {
        return likesId;
    }

    public void setLikesId(BmobRelation likesId) {
        this.likesId = likesId;
    }

    public Integer getLikeSum() {
        return likeSum;
    }

    public void setLikeSum(Integer likeSum) {
        this.likeSum = likeSum;
    }

}
