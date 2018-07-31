package com.ls.www.petcommunity.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class tb_user_followers extends BmobObject {

    private String userId;
    private BmobRelation followerId;
    private Integer followerSum = 0;
    private _User user;
    private Integer messageFansSum = 0;
    private Integer messageFansRead = 0;
    private Integer messageTopicsSum = 0;
    private Integer messageTopicsRead = 0;



    private Integer messageNotesSum = 0;
    private Integer messageNotesRead = 0;
    private Integer notificationRead = 0;

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

    public Integer getMessageTopicsSum() {
        return messageTopicsSum;
    }

    public void setMessageTopicsSum(Integer messageTopicsSum) {
        this.messageTopicsSum = messageTopicsSum;
    }

    public Integer getMessageTopicsRead() {
        return messageTopicsRead;
    }

    public void setMessageTopicsRead(Integer messageTopicsRead) {
        this.messageTopicsRead = messageTopicsRead;
    }

    public Integer getMessageNotesSum() {
        return messageNotesSum;
    }

    public void setMessageNotesSum(Integer messageNotesSum) {
        this.messageNotesSum = messageNotesSum;
    }

    public Integer getMessageNotesRead() {
        return messageNotesRead;
    }

    public void setMessageNotesRead(Integer messageNotesRead) {
        this.messageNotesRead = messageNotesRead;
    }

    public Integer getNotificationRead() {
        return notificationRead;
    }

    public void setNotificationRead(Integer notificationRead) {
        this.notificationRead = notificationRead;
    }

}
