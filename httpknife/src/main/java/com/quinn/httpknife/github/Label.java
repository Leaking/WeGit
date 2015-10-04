package com.quinn.httpknife.github;

import java.io.Serializable;

/**
 * Created by Quinn on 10/4/15.
 */
public class Label implements Serializable {
    private static final long serialVersionUID = 105838111015760693L;

    private String color;
    private String name;
    private String url;

    public Label() {
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        } else if(!(obj instanceof Label)) {
            return false;
        } else {
            String name = this.name;
            return name != null && name.equals(((Label)obj).name);
        }
    }

    public int hashCode() {
        String name = this.name;
        return name != null?name.hashCode():super.hashCode();
    }

    public String toString() {
        String name = this.name;
        return name != null?name:super.toString();
    }

    public String getColor() {
        return this.color;
    }

    public Label setColor(String color) {
        this.color = color;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Label setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public Label setUrl(String url) {
        this.url = url;
        return this;
    }
}
