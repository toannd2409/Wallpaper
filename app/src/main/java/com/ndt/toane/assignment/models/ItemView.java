package com.ndt.toane.assignment.models;

public class ItemView {
    private int url;
    private String countLike;
    private String countView;

    public ItemView(int url, String countLike, String countView) {
        this.url = url;
        this.countLike = countLike;
        this.countView = countView;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getCountLike() {
        return countLike;
    }

    public void setCountLike(String countLike) {
        this.countLike = countLike;
    }

    public String getCountView() {
        return countView;
    }

    public void setCountView(String countView) {
        this.countView = countView;
    }
}
