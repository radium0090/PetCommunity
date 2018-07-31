package com.ls.www.petcommunity.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class _User extends BmobUser {

    private BmobFile headPortrait;
    private BmobFile coverPage;
    private String nickName;
    private String briefIntro;
    private BmobRelation focusId;



    private BmobRelation focusNote;
    private BmobRelation focusTopic;
    private Integer focusIdSum = 0;
    private tb_user_followers followerId;
    private BmobRelation myCollection;

    public BmobFile getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(BmobFile headPortrait) {
        this.headPortrait = headPortrait;
    }

    public BmobFile getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(BmobFile coverPage) {
        this.coverPage = coverPage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBriefIntro() {
        return briefIntro;
    }

    public void setBriefIntro(String briefIntro) {
        this.briefIntro = briefIntro;
    }

    public BmobRelation getFocusId() {
        return focusId;
    }

    public void setFocusId(BmobRelation focusId) {
        this.focusId = focusId;
    }

    public BmobRelation getFocusNote() {
        return focusNote;
    }

    public void setFocusNote(BmobRelation focusNote) {
        this.focusNote = focusNote;
    }

    public BmobRelation getFocusTopic() {
        return focusTopic;
    }

    public void setFocusTopic(BmobRelation focusTopic) {
        this.focusTopic = focusTopic;
    }

    public Integer getFocusIdSum() {
        return focusIdSum;
    }

    public void setFocusIdSum(Integer focusIdSum) {
        this.focusIdSum = focusIdSum;
    }

    public tb_user_followers getFollowerId() {
        return followerId;
    }

    public void setFollowerId(tb_user_followers followerId) {
        this.followerId = followerId;
    }

    public BmobRelation getMyCollection() {
        return myCollection;
    }

    public void setMyCollection(BmobRelation myCollection) {
        this.myCollection = myCollection;
    }
}
