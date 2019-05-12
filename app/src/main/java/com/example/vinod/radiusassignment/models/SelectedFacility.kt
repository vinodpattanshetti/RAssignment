package com.example.vinod.radiusassignment.models

import java.io.Serializable

class SelectedFacility : Serializable {

  var facilityType: String? = null
  var facilityName: String? = null
  var facilityId: String? = null

  constructor() {

  }

  constructor(facilityType: String, facilityName: String) {
    this.facilityType = facilityType
    this.facilityName = facilityName
  }
}
