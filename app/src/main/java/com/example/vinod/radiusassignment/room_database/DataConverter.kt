package com.example.vinod.radiusassignment.room_database

import android.arch.persistence.room.TypeConverter
import com.example.vinod.radiusassignment.models.Exclusion
import com.example.vinod.radiusassignment.models.FacilitiesModel
import com.example.vinod.radiusassignment.models.Facility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
  @TypeConverter
  fun fromFacilityModel(value: FacilitiesModel?): String? {
    val gson = Gson()
    val type = object : TypeToken<FacilitiesModel>() {}.type
    return gson.toJson(value, type)
  }

  @TypeConverter
  fun toFacilityModel(value: String?): FacilitiesModel? {
    val gson = Gson()
    val type = object : TypeToken<FacilitiesModel>() {}.type
    return gson.fromJson(value, type)
  }

  @TypeConverter
  fun fromFacility(value: ArrayList<Facility>?): String? {
    val  gson = Gson()
    val type = object : TypeToken<ArrayList<Facility>?>(){}.type
    return gson.toJson(value, type)
  }

  @TypeConverter
  fun toFacility(value: String?): ArrayList<Facility>? {
    val gson = Gson()
    val type = object : TypeToken<ArrayList<Facility>?>() {}.type
    return gson.fromJson(value, type)
  }

  @TypeConverter
  fun fromExclusion(value: ArrayList<ArrayList<Exclusion>>?): String? {
    val  gson = Gson()
    val type = object : TypeToken<ArrayList<ArrayList<Exclusion>>?>(){}.type
    return gson.toJson(value, type)
  }

  @TypeConverter
  fun toExclusion(value: String?): ArrayList<ArrayList<Exclusion>>? {
    val gson = Gson()
    val type = object : TypeToken<ArrayList<ArrayList<Exclusion>>?>() {}.type
    return gson.fromJson(value, type)
  }

}
