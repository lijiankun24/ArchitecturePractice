package com.lijiankun24.architecturepractice.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Girl.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */
@Entity(tableName = "girls")
public class Girl {

    @PrimaryKey
    private String mId;

    private String mName;

    private String mAvatar;

    private int mAge;

    private String mFrom;

    public Girl() {
    }

    public Girl(Girl girl) {
        this.mId = girl.getId();
        this.mName = girl.getName();
        this.mAge = girl.getAge();
        this.mFrom = girl.getFrom();
        this.mAvatar = girl.getAvatar();
    }

    @Ignore
    public Girl(String id, String name,
                String avatar, int age,
                String from) {
        mId = id;
        mName = name;
        mAvatar = avatar;
        mAge = age;
        mFrom = from;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getFrom() {
        return mFrom;
    }

    public void setFrom(String from) {
        mFrom = from;
    }
}
