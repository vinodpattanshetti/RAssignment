package com.example.vinod.radiusassignment.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat

import com.example.vinod.radiusassignment.R
import com.example.vinod.radiusassignment.constants.Constants
import com.example.vinod.radiusassignment.databinding.ActivityMain2Binding
import com.example.vinod.radiusassignment.models.SelectedFacility
import com.example.vinod.radiusassignment.utils.orDefaultInt

import java.util.ArrayList

class SelectedResultDetailActivity : AppCompatActivity() {

  private var mBinder: ActivityMain2Binding? = null
  private var mSelectedFacilityList: ArrayList<SelectedFacility>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinder = DataBindingUtil.setContentView(this, R.layout.activity_main2)
    mBinder?.tbToolbar?.run {
      title = getString(R.string.txt_selected_facilities)
      setTitleTextColor(
        ContextCompat.getColor(
          this@SelectedResultDetailActivity,
          R.color.color_white
        )
      )
      setNavigationOnClickListener {
        finish()
      }
    }
    mSelectedFacilityList =
        intent.getSerializableExtra("SelectedFacilityList_Key") as ArrayList<SelectedFacility>

    if (null != mSelectedFacilityList && mSelectedFacilityList?.size == 2) {
      setViews()
    } else if (null != mSelectedFacilityList && mSelectedFacilityList?.size == 3) {
      setViews()
    }
  }

  private fun setViews() {
    if (type(Constants.PROPERTY_TYPE)) {
      mBinder?.tvPropertyType?.text =
          StringBuilder().append(Constants.PROPERTY_TYPE_VALUE).append(" : ")
            .append(mSelectedFacilityList?.get(0)?.facilityName).toString()
    }
    if (type(Constants.NUMBER_OF_ROOMS)) {
      mBinder?.tvNumberOfRoomsType?.text =
          StringBuilder().append(Constants.NUMBER_OF_ROOMS_VALUE).append(" : ")
            .append(mSelectedFacilityList?.get(1)?.facilityName).toString()
    }
    if (type(Constants.OTHER_FACILITIES)) {
      mBinder?.tvOtherFacilitiesType?.text =
          StringBuilder().append(Constants.OTHER_FACILITIES_VALUE).append(" : ")
            .append(mSelectedFacilityList?.get(2)?.facilityName).toString()
    }
  }

  private fun type(type: String): Boolean {
    return ((type == mSelectedFacilityList?.get(0)?.facilityType) || (type == mSelectedFacilityList?.get(
      1
    )?.facilityType) || (mSelectedFacilityList?.size.orDefaultInt() > 2 && type == mSelectedFacilityList?.get(
      2
    )?.facilityType))
  }
}
