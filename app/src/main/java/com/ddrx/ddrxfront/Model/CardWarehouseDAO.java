package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/15.
 */
@Dao
public interface CardWarehouseDAO {
    @Query("Select * From CardWarehouse")
    List<CardWarehouse> queryAllCardWarehouse();

    @Query("Select * From CardWarehouse Where CW_id = (:CW_id)")
    CardWarehouse queryCardWarehouseById(Long CW_id);

    @Query("Select CW_id From CardWarehouse")
    List<Long> queryAllCW_ID();

    @Query("Select CW_id FROM CardWarehouse Where CW_id not in (:CW_IDs)")
    List<Long> queryAllNotExistCW_ID(List<Long> CW_IDs);

    @Query("Update CardWarehouse Set CW_cover_url = :cover_url Where CW_id = :id")
    void updateCoverUrl(long id, String cover_url);

    @Query("Delete From CardWarehouse Where CW_id in (:CW_IDs)")
    void deleteCardWarehouseById(List<Long> CW_IDs);

    @Insert
    void insertCardWarehouse(CardWarehouse... cardWarehouse);

    @Insert
    void insertMultipleCardWarehouse(List<CardWarehouse> mul_cardWarehouse);

    @Update
    void updateCardWarehouse(CardWarehouse... cardWarehouses);

    @Delete
    void deleteCardWarehouse(CardWarehouse... cardWarehouses);
}
