package com.example.vinod.radiusassignment.room_database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.vinod.radiusassignment.models.FacilitiesModel
import android.arch.persistence.room.Room
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = [FacilitiesModel::class], version = 2, exportSchema = false)

@TypeConverters(DataConverter::class)
abstract class FacilityDatabase : RoomDatabase() {

  abstract fun getIFacilityDao(): FacilityDao
  private var INSTANCE: FacilityDatabase? = null

  fun getDatabase(context: Context): FacilityDatabase? {
    if (INSTANCE == null) {
      synchronized(FacilityDatabase::class.java) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(
            context.applicationContext, FacilityDatabase::class.java, "facility_database"
          ).build()
        }
      }
    }
    return INSTANCE
  }

}