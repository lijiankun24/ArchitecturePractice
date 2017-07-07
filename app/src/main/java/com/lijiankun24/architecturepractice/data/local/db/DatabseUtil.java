package com.lijiankun24.architecturepractice.data.local.db;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DatabseUtil.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

public class DatabseUtil {

    private static final int DEFAULT_AGE = 18;

    private static final String DEFAULT_FROM = "Moon";

    private static final List<String> mAvatars = new ArrayList<>();

    static void initializeDb(AppDatabase db) {
        List<Girl> girls = new ArrayList<>();
        generateGirls(girls);
        insertGirlData(db, girls);
    }

    private static void insertGirlData(AppDatabase db, List<Girl> girls) {
        db.beginTransaction();
        try {
            db.girlDao().insertGirls(girls);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private static void generateGirls(List<Girl> girlList) {
        setUpData();
        for (int i = 0; i < 10; i++) {
            String name = "Girl" + i;
            String avatar = mAvatars.get(i);
            Girl girl = new Girl(UUID.randomUUID().toString(), name, avatar, DEFAULT_AGE, DEFAULT_FROM);
            girlList.add(girl);
        }
    }

    private static void setUpData() {
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-27-17934080_117414798808566_8957027985114791936_n.jpg?imageslim");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-25-13651793_897557617014845_571817176_n.jpg");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-24-18013547_1532023163498554_215541963087151104_n.jpg");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-24-18094714_158946097967074_5909424912493182976_n.jpg");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-21-18013964_1389732981073150_4044061109068496896_n.jpg");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-20-17932350_1238028809599424_2089669844847820800_n.jpg");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-19-17881407_1845958195665029_1132383288824954880_n.jpg");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-18-17882540_190116561497334_440657494176432128_n.jpg");
        mAvatars.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
    }
}
