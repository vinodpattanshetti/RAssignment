package com.example.vinod.radiusassignment.room_database

import android.arch.persistence.room.Dao
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.vinod.radiusassignment.models.FacilitiesModel

@Dao
public interface FacilityDao {
  @Insert fun insertProduct(mFacilitiesModel: FacilitiesModel)

  @Query("SELECT * FROM FacilitiesModel")
  fun fetchAllTasks(): LiveData<FacilitiesModel>
}