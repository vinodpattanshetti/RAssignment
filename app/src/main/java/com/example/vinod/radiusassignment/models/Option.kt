package com.example.vinod.radiusassignment.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull

@Entity(tableName = "options") data class Option(
  @PrimaryKey @NonNull @SerializedName("name") @Expose var name: String? = null,
  @SerializedName("icon") @Expose var icon: String? = null, @SerializedName("id") @Expose
  var id: String? = null,

  var facilityType: String? = null
)
