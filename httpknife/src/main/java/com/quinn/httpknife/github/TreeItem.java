package com.quinn.httpknife.github;

/**
 * Created by Quinn on 8/7/15.
 */
public class TreeItem {

    private static final long serialVersionUID = 6518261551932913340L;

    public static final String MODE_TREE = "tree";
    public static final String MODE_BLOB = "blob";

    private String path;
    private String mode;
    private String type;
    private String sha;
    private long size;
    private String url;

    public TreeItem() {

    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "TreeItem{" +
                "mode='" + mode + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", sha='" + sha + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                '}';
    }
}



