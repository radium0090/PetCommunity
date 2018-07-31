package com.ls.www.petcommunity.model;

public class TopicModel {

    private String topicId;
    private String content;

    public TopicModel(String topicId, String content) {
        this.topicId = topicId;
        this.content = content;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
