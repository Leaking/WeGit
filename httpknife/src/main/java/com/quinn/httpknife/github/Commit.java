package com.quinn.httpknife.github;

/**
 * Created by Quinn on 9/25/15.
 */
public class Commit {

    private static final long serialVersionUID = 7285665432528832240L;

    private String sha;
    private String url;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "sha='" + sha + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
