package com.ls.www.petcommunity.model.table;

import cn.bmob.v3.BmobObject;

public class tb_message_topics extends BmobObject {

    private _User initiator;
    private String acceptorId;
    private String topicId;
    private String topicContent;

    public _User getInitiator() {
        return initiator;
    }

    public void setInitiator(_User initiator) {
        this.initiator = initiator;
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

}
