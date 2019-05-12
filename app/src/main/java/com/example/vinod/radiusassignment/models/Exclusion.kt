package com.example.vinod.radiusassignment.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull

@Entity(tableName = "exclusions")
data class Exclusion(
  @PrimaryKey
  @NonNull
  @SerializedName("facility_id") @Expose var facilityId: String? = null,
  @SerializedName("options_id") @Expose var optionsId: String? = null
)
