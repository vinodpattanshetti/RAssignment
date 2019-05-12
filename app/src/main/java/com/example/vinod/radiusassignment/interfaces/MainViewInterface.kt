package com.example.vinod.radiusassignment.interfaces

import com.example.vinod.radiusassignment.models.FacilitiesModel

interface MainViewInterface {

  fun showToast(s: String)
  fun getFacilities(mFacilitiesModel: FacilitiesModel?)
  fun displayError(s: String)

}
