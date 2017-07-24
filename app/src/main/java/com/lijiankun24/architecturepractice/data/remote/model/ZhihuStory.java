package com.lijiankun24.architecturepractice.data.remote.model;

/**
 * ZhihuStory.java
 * <p>
 * Created by lijiankun on 17/7/23.
 */
public class ZhihuStory {

    private String id;

    private int type;

    private String ga_prefix;

    private String title;

    private String[] images;

    public ZhihuStory(String id, int type, String ga_prefix,
                      String title, String[] images) {
        this.id = id;
        this.type = type;
        this.ga_prefix = ga_prefix;
        this.title = title;
        this.images = images;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
