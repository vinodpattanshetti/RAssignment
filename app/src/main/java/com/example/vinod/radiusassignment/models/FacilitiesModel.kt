package com.example.vinod.radiusassignment.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull

@Entity(tableName = "FacilitiesModel")
data class FacilitiesModel(
  @PrimaryKey(autoGenerate = true)
  @NonNull
  @SerializedName("id")
  var id: Int = 0,
  @SerializedName("facilities") @Expose var facilities: ArrayList<Facility>? = null,
  @SerializedName("exclusions") @Expose var exclusions: ArrayList<ArrayList<Exclusion>>? = null
)
