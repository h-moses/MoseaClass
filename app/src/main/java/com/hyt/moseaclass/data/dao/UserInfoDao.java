package com.hyt.moseaclass.data.dao;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hyt.moseaclass.data.entity.UserInfo;

@Dao
public interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfo userInfo);

    @Query("SELECT * FROM userInfo WHERE u_id = :uid")
    LiveData<UserInfo> query(Integer uid);

    @Query("SELECT u_login FROM userInfo WHERE u_id = :uid")
    LiveData<Integer> queryLogin(Integer uid);

    @Update
    void updateUser(UserInfo userInfo);
}
