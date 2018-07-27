package com.ls.www.petcommunity.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class tb_user_followers extends BmobObject {

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BmobRelation getFollowerId() {
        return followerId;
    }

    public void setFollowerId(BmobRelation followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowerSum() {
        return followerSum;
    }

    public void setFollowerSum(Integer followerSum) {
        this.followerSum = followerSum;
    }

    public _User getUser() {
        return user;
    }

    public void setUser(_User user) {
        this.user = user;
    }

    public Integer getMessageFansSum() {
        return messageFansSum;
    }

    public void setMessageFansSum(Integer messageFansSum) {
        this.messageFansSum = messageFansSum;
    }

    public Integer getMessageFansRead() {
        return messageFansRead;
    }

    public void setMessageFansRead(Integer messageFansRead) {
        this.messageFansRead = messageFansRead;
    }

    public Integer getMessageSayingsSum() {
        return messageSayingsSum;
    }

    public void setMessageSayingsSum(Integer messageSayingsSum) {
        this.messageSayingsSum = messageSayingsSum;
    }

    public Integer getMessageSayingsSead() {
        return messageSayingsSead;
    }

    public void setMessageSayingsSead(Integer messageSayingsSead) {
        this.messageSayingsSead = messageSayingsSead;
    }

    public Integer getMessageBooksSum() {
        return messageBooksSum;
    }

    public void setMessageBooksSum(Integer messageBooksSum) {
        this.messageBooksSum = messageBooksSum;
    }

    public Integer getMessageBooksRead() {
        return messageBooksRead;
    }

    public void setMessageBooksRead(Integer messageBooksRead) {
        this.messageBooksRead = messageBooksRead;
    }

    public Integer getNotificationRead() {
        return notificationRead;
    }

    public void setNotificationRead(Integer notificationRead) {
        this.notificationRead = notificationRead;
    }

    private String userId;
    private BmobRelation followerId;
    private Integer followerSum = 0;
    private _User user;
    private Integer messageFansSum = 0;
    private Integer messageFansRead = 0;
    private Integer messageSayingsSum = 0;
    private Integer messageSayingsSead = 0;
    private Integer messageBooksSum = 0;
    private Integer messageBooksRead = 0;
    private Integer notificationRead = 0;


}
