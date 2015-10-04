package com.quinn.httpknife.github;

import com.quinn.httpknife.payload.Payload;

import java.util.Date;

public class Event {


    private String id;
    private String type;
    private User actor;
    private Repository repo;
    private Date created_at;
    private Payload payload;
    private User org;



    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Repository getRepo() {
        return repo;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public User getOrg() {
        return org;
    }

    public void setOrg(User org) {
        this.org = org;
    }

    @Override
    public String toString() {
        return "Event{" +
                "actor=" + actor +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", repo=" + repo +
                ", created_at=" + created_at +
                ", payload=" + payload +
                ", org=" + org +
                '}';
    }
}
