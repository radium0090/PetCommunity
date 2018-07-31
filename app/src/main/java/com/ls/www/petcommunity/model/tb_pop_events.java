package com.ls.www.petcommunity.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class tb_pop_events extends BmobObject {

    private String name;
    private BmobFile image;
    private BmobFile intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobFile getIntro() {
        return intro;
    }

    public void setIntro(BmobFile intro) {
        this.intro = intro;
    }
}
