package com.quinn.httpknife.payload;

import com.quinn.httpknife.github.Issue;

/**
 * Created by Quinn on 10/4/15.
 */
public class IssuePayload extends Payload {

    private static final long serialVersionUID = 4412536275423825042L;
    private String action;
    private Issue issue;

    public IssuePayload() {
    }

    public String getAction() {
        return this.action;
    }

    public IssuePayload setAction(String action) {
        this.action = action;
        return this;
    }

    public Issue getIssue() {
        return this.issue;
    }

    public IssuePayload setIssue(Issue issue) {
        this.issue = issue;
        return this;
    }
}
