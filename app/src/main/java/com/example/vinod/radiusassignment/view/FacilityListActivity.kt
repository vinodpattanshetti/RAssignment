package com.example.vinod.radiusassignment.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.vinod.radiusassignment.R
import com.example.vinod.radiusassignment.adapter.FacilitiesAdapter
import com.example.vinod.radiusassignment.constants.Constants.DATABASE_ENTRY_TIME_KEY
import com.example.vinod.radiusassignment.constants.Constants.NUMBER_OF_ROOMS_VALUE
import com.example.vinod.radiusassignment.constants.Constants.NUM_OF_HOURS_IN_MILLISECONDS
import com.example.vinod.radiusassignment.constants.Constants.OTHER_FACILITIES_VALUE
import com.example.vinod.radiusassignment.constants.Constants.PROPERTY_TYPE_VALUE
import com.example.vinod.radiusassignment.constants.Constants.SOMETHING_WENT_WRONG_KEY
import com.example.vinod.radiusassignment.databinding.ActivityMainBinding
import com.example.vinod.radiusassignment.interfaces.MainViewInterface
import com.example.vinod.radiusassignment.models.FacilitiesModel
import com.example.vinod.radiusassignment.models.SelectedFacility
import com.example.vinod.radiusassignment.presenter.MainPresenter
import com.example.vinod.radiusassignment.room_database.FacilityRepository
import com.example.vinod.radiusassignment.utils.*
import java.util.*

class FacilityListActivity : AppCompatActivity(), MainViewInterface,
  FacilitiesAdapter.IActivityCommunicator {
  private var mBinder: ActivityMainBinding? = null

  private var mMainPresenter: MainPresenter? = null
  private var mFacilityRepository: FacilityRepository? = null
  private var mSelectedFacilityList: ArrayList<SelectedFacility?>? = null
  private var mFacilitiesModel: FacilitiesModel? = null

  private var checkFirstExclusion = false
  private var secondFirstExclusion = false
  private var thirdFirstExclusion = false

  private var currentTime: Long? = null
  private var mSharedPreference: SharedPreference? = null

  companion object {
    private var PROPERTY_TYPE = "1"
    private var NUMBER_OF_ROOMS = "2"
    private var OTHER_FACILITIES = "3"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    currentTime = System.currentTimeMillis()
    mBinder = DataBindingUtil.setContentView(this, R.layout.activity_main)
    mSharedPreference = SharedPreference.getInstance()
    mBinder?.tbToolbar?.title = getString(R.string.txt_facilities_list)
    mBinder?.tbToolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.color_white))
    mBinder?.pbProgress?.showView()
    mMainPresenter = MainPresenter(this@FacilityListActivity)
    mFacilityRepository = FacilityRepository(applicationContext)
    initViews()
    getFacilitiesFromDatabase()
  }

  /**
   *  The function `getFacilitiesFromDatabase` to get the FacilitiesModel from database. If data is't available in database fetch from
   *  non static api https://my-json-server.typicode.com/iranjith4/ad-assignment/db
   */
  private fun getFacilitiesFromDatabase() {
    mFacilityRepository?.getFacilityModel()
      ?.observe(this, Observer<FacilitiesModel> { mFacilitiesModel ->
        mBinder?.pbProgress?.hideView()
        if (callApiAfterOneDay()) {
          mBinder?.pbProgress?.showView()
          mMainPresenter?.getFacilities()
        } else if (null != mFacilitiesModel && mFacilitiesModel.facilities.isNullOrEmpty() && mFacilitiesModel.exclusions.isNullOrEmpty()) {
          mBinder?.pbProgress?.showView()
          mMainPresenter?.getFacilities()
        } else {
          initAdapter(mFacilitiesModel)
        }
      })
  }

  private fun callApiAfterOneDay(): Boolean {
    val mLastSavedInstanceTime = mSharedPreference?.getSharedPrefLong(DATABASE_ENTRY_TIME_KEY)
    if ((mLastSavedInstanceTime?.minus(currentTime.orDefaultLong())).orDefaultLong() >= NUM_OF_HOURS_IN_MILLISECONDS) {
      return true
    }
    return false
  }

  /**
   * Initialize all views for data binding
   */
  private fun initViews() {
    mSelectedFacilityList = ArrayList()
    mBinder?.run {
      rvPropertyType.layoutManager = LinearLayoutManager(this@FacilityListActivity)
      rvNumberOfRooms.layoutManager = LinearLayoutManager(this@FacilityListActivity)
      rvOtherFacilities.layoutManager = LinearLayoutManager(this@FacilityListActivity)
      tvPropertyType.text = PROPERTY_TYPE_VALUE
      tvNumberOfRooms.text = NUMBER_OF_ROOMS_VALUE
      tvOtherFacilities.text = OTHER_FACILITIES_VALUE
      btSubmit.setOnClickListener {
        if (null != mSelectedFacilityList && mSelectedFacilityList?.size.orDefaultInt() >= 2) {
          checkFirstExclusion = false
          secondFirstExclusion = false
          thirdFirstExclusion = false
          checkExclusion()
        }
        if (mSelectedFacilityList?.size == 2 && checkFirstExclusion) {
          callSecondPage()
        } else if (mSelectedFacilityList?.size == 2 && secondFirstExclusion) {
          callSecondPage()
        } else if (mSelectedFacilityList?.size == 3 && checkFirstExclusion) {
          callSecondPage()
        } else if (mSelectedFacilityList?.size == 3 && secondFirstExclusion) {
          callSecondPage()
        } else if (mSelectedFacilityList?.size == 3 && thirdFirstExclusion) {
          callSecondPage()
        } else {
          Toast.makeText(this@FacilityListActivity, "Select proper combination", Toast.LENGTH_LONG).show()
        }
      }
    }
  }

  private fun callSecondPage() {
    val intent = Intent(this@FacilityListActivity, SelectedResultDetailActivity::class.java)
    intent.putExtra("SelectedFacilityList_Key", mSelectedFacilityList)
    startActivity(intent)
  }

  /**
   * The function `getFacilities` to fetch data from facility api
   */
  override fun getFacilities(mFacilitiesModel: FacilitiesModel?) {
    mBinder?.pbProgress?.hideView()
    this.mFacilitiesModel = mFacilitiesModel
    val dbFacilitiesModel = FacilitiesModel(
      facilities = mFacilitiesModel?.facilities, exclusions = mFacilitiesModel?.exclusions
    )
    mFacilityRepository?.insertFacilityModel(dbFacilitiesModel)
    mSharedPreference?.putSharedPrefLong(DATABASE_ENTRY_TIME_KEY, System.currentTimeMillis())
    initAdapter(mFacilitiesModel)
  }

  /**
   *  The function `initAdapter` is to initialise adapters
   */
  private fun initAdapter(mFacilitiesModel: FacilitiesModel?) {
    this.mFacilitiesModel = mFacilitiesModel
    mBinder?.run {
      btSubmit.isEnabled = true
      pbProgress.visibility = View.GONE
      // PropertyType
      val propertyTypeAdapter = mMainPresenter?.formListData(mFacilitiesModel, "1")?.let {
        FacilitiesAdapter(
          it, PROPERTY_TYPE, this@FacilityListActivity, mMainPresenter?.formImageListFortFirstFacilities(
            R.drawable.apartment, R.drawable.condo, R.drawable.boat, R.drawable.land
          )
        )
      }
      rvPropertyType.adapter = propertyTypeAdapter

      // Number of Rooms
      val numberOfRoomsAdapter = mMainPresenter?.formListData(mFacilitiesModel, "2")?.let {
        FacilitiesAdapter(
          it, NUMBER_OF_ROOMS, this@FacilityListActivity, mMainPresenter?.formImageListForSecondFacilities(
            R.drawable.rooms, R.drawable.rooms_not_available
          )
        )
      }
      rvNumberOfRooms.adapter = numberOfRoomsAdapter

      // Other facilities
      val otherFacilitiesAdapter = mMainPresenter?.formListData(mFacilitiesModel, "3")?.let {
        FacilitiesAdapter(
          it,
          OTHER_FACILITIES,
          this@FacilityListActivity,
          mMainPresenter?.formImageListFortThirdFacilities(
            R.drawable.swimming,
            R.drawable.garden,
            R.drawable.garage
          )
        )
      }
      rvOtherFacilities.adapter = otherFacilitiesAdapter
    }
  }

  override fun displayError(s: String) {
    mBinder?.pbProgress?.hideView()
    Toast.makeText(this, SOMETHING_WENT_WRONG_KEY, Toast.LENGTH_LONG).show()
  }

  override fun showToast(s: String) {
    Toast.makeText(this, SOMETHING_WENT_WRONG_KEY, Toast.LENGTH_LONG).show()
  }

  private fun checkExclusion() {
    val mFirstExclusion = mFacilitiesModel?.exclusions?.get(0)
    val first = mFirstExclusion?.get(0)?.optionsId
    val second = mFirstExclusion?.get(1)?.optionsId
    if (first?.let { isOptionIdAvailable(it) }.orFalse() || second?.let { isOptionIdAvailable(it) }.orFalse()) {
      checkFirstExclusion = true
    }
    if (checkFirstExclusion) {
      val mSecondExclusion = mFacilitiesModel?.exclusions?.get(1)
      val third = mSecondExclusion?.get(0)?.optionsId
      val fourth = mSecondExclusion?.get(1)?.optionsId
      if (third?.let { isOptionIdAvailable(it) }.orFalse() || fourth?.let { isOptionIdAvailable(it) }.orFalse()) {
        secondFirstExclusion = true
      }
    }
    if (checkFirstExclusion && secondFirstExclusion) {
      val mThirdExclusion = mFacilitiesModel?.exclusions?.get(2)
      val fifth = mThirdExclusion?.get(0)?.optionsId
      val sixth = mThirdExclusion?.get(1)?.optionsId
      if (fifth?.let { isOptionIdAvailable(it) }.orFalse() || sixth?.let { isOptionIdAvailable(it) }.orFalse()) {
        thirdFirstExclusion = true
      }
    }
  }

  private fun isOptionIdAvailable(optionId: String): Boolean {
    mSelectedFacilityList?.let {
      for (i in it.indices) {
        if (mSelectedFacilityList?.get(i)?.facilityId == optionId) {
          return false
        }
      }
    }
    return true
  }

  override fun sendSelectedFacility(mSelectedFacility: SelectedFacility?) {
    mSelectedFacilityList?.let {
      for (i in it.indices) {
        if (it[i]?.facilityType == mSelectedFacility?.facilityType) {
          it.removeAt(i)
          break
        }
      }
      it.add(mSelectedFacility)
    }
  }
}
