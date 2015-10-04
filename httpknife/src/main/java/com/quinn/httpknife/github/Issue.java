package com.quinn.httpknife.github;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Quinn on 10/4/15.
 */
public class Issue implements Serializable {

    private static final long serialVersionUID = 6736401806450165758L;

    private long id;
    private Date closedAt;
    private Date createdAt;
    private Date updatedAt;
    private int comments;
    private int number;
    private List<Label> labels;
    private String body;
    private String bodyHtml;
    private String bodyText;
    private String htmlUrl;
    private String state;
    private String title;
    private String url;
    private User assignee;
    private User user;

    public Issue() {
    }

    public Date getClosedAt() {
        return this.closedAt;
    }

    public Issue setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Issue setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public Issue setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public int getComments() {
        return this.comments;
    }

    public Issue setComments(int comments) {
        this.comments = comments;
        return this;
    }

    public int getNumber() {
        return this.number;
    }

    public Issue setNumber(int number) {
        this.number = number;
        return this;
    }

    public List<Label> getLabels() {
        return this.labels;
    }

    public Issue setLabels(List<Label> labels) {
        this.labels = labels != null?new ArrayList(labels):null;
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public Issue setBody(String body) {
        this.body = body;
        return this;
    }

    public String getBodyHtml() {
        return this.bodyHtml;
    }

    public Issue setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
        return this;
    }

    public String getBodyText() {
        return this.bodyText;
    }

    public Issue setBodyText(String bodyText) {
        this.bodyText = bodyText;
        return this;
    }

    public String getHtmlUrl() {
        return this.htmlUrl;
    }

    public Issue setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
        return this;
    }

    public String getState() {
        return this.state;
    }

    public Issue setState(String state) {
        this.state = state;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Issue setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public Issue setUrl(String url) {
        this.url = url;
        return this;
    }

    public User getAssignee() {
        return this.assignee;
    }

    public Issue setAssignee(User assignee) {
        this.assignee = assignee;
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public Issue setUser(User user) {
        this.user = user;
        return this;
    }

    public long getId() {
        return this.id;
    }

    public Issue setId(long id) {
        this.id = id;
        return this;
    }

    public String toString() {
        return "Issue " + this.number;
    }
}
