package com.ls.www.petcommunity.model;

import cn.bmob.v3.BmobObject;

public class tb_notification extends BmobObject {

    private String content;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
