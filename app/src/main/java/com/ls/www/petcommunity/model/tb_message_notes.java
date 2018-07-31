package com.ls.www.petcommunity.model;

import cn.bmob.v3.BmobObject;

public class tb_message_notes extends BmobObject {

    private String userId;
    private String userName;
    private String acceptorId;
    private tb_collection focusNote;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }

    public tb_collection getFocusNote() {
        return focusNote;
    }

    public void setFocusNote(tb_collection focusNote) {
        this.focusNote = focusNote;
    }
}
