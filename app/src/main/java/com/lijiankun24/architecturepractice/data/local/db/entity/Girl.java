package com.lijiankun24.architecturepractice.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Girl.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */
@Entity(tableName = "girls")
public class Girl {

    @NonNull
    @PrimaryKey
    private String _id;

    private String createdAt;

    private String desc;

    private String publishedAt;

    private String source;

    private String type;

    private String url;

    private boolean used;

    private String who;

    private int mAge;

    public Girl() {
    }

    public Girl(Girl girl) {
        this._id = girl.get_id();
        this.createdAt = girl.getCreatedAt();
        this.desc = girl.getDesc();
        this.publishedAt = girl.getPublishedAt();
        this.source = girl.getSource();
        this.type = girl.getType();
        this.url = girl.getUrl();
        this.used = girl.isUsed();
        this.who = girl.getWho();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String id) {
        this._id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }
}
