package com.ls.www.petcommunity.model;

import cn.bmob.v3.BmobObject;

public class tb_message_fans extends BmobObject {

    private _User initiator;
    private _User acceptor;
    private String acceptorId;

    public _User getInitiator() {
        return initiator;
    }

    public void setInitiator(_User initiator) {
        this.initiator = initiator;
    }

    public _User getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(_User acceptor) {
        this.acceptor = acceptor;
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }
}
