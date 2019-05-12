package com.example.vinod.radiusassignment.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull

@Entity(tableName = "facilities")
data class Facility(
  @PrimaryKey
  @NonNull
  @SerializedName("facility_id") @Expose var facilityId: String? = null, @SerializedName("name")
  @Expose var name: String? = null, @SerializedName("options") @Expose
  var options: List<Option>? = null
)
