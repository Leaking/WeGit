package com.quinn.httpknife.payload;

import com.quinn.httpknife.github.User;

/**
 * Created by Quinn on 15/10/2.
 */
public class MenberPayload extends Payload {
    private static final long serialVersionUID = 8916400800708594462L;

    public MenberPayload(){

    }

    private User member;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "MenberPayload{" +
                "action='" + action + '\'' +
                ", member=" + member +
                '}';
    }
}
