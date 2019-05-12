package com.example.vinod.radiusassignment.room_database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import com.example.vinod.radiusassignment.models.FacilitiesModel

class FacilityRepository(mContext: Context) {
  private var mFacilityDatabase: FacilityDatabase? = null
  private val name = "facilities_task"

  init {
    mFacilityDatabase =
        Room.databaseBuilder<FacilityDatabase>(mContext, FacilityDatabase::class.java, name).fallbackToDestructiveMigration().build()
  }

  fun insertFacilityModel(mFacilitiesModel: FacilitiesModel) {
    object : AsyncTask<Void, Void, Void>() {
      override fun doInBackground(vararg voids: Void): Void? {
        mFacilityDatabase?.getIFacilityDao()?.insertProduct(mFacilitiesModel)
        return null
      }
    }.execute()
  }

  fun getFacilityModel(): LiveData<FacilitiesModel>? {
    return mFacilityDatabase?.getIFacilityDao()?.fetchAllTasks()
  }


}