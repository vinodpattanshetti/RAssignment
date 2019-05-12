package com.example.vinod.radiusassignment.presenter

import android.util.Log
import com.example.vinod.radiusassignment.R

import com.example.vinod.radiusassignment.interfaces.MainPresenterInterface
import com.example.vinod.radiusassignment.interfaces.MainViewInterface
import com.example.vinod.radiusassignment.models.FacilitiesModel
import com.example.vinod.radiusassignment.models.Option
import com.example.vinod.radiusassignment.network.NetworkClient
import com.example.vinod.radiusassignment.network.NetworkInterface
import com.example.vinod.radiusassignment.utils.orDefaultInt

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class MainPresenter(private val mMainViewInterface: MainViewInterface) : MainPresenterInterface {
  private val TAG = "MainPresenter"

  private val observable: Observable<FacilitiesModel>
    get() = NetworkClient.getRetrofit()!!.create(NetworkInterface::class.java).facilities.subscribeOn(
      Schedulers.io()
    ).observeOn(AndroidSchedulers.mainThread())

  private val observer: DisposableObserver<FacilitiesModel>
    get() = object : DisposableObserver<FacilitiesModel>() {

      override fun onNext(mFacilitiesModel: FacilitiesModel) {
        Log.d(TAG, "OnNext$mFacilitiesModel")
        mMainViewInterface.getFacilities(mFacilitiesModel)
      }

      override fun onError(e: Throwable) {
        Log.d(TAG, "Error$e")
        e.printStackTrace()
        mMainViewInterface.displayError("Error fetching Facilities Data")
      }

      override fun onComplete() {
        Log.d(TAG, "Completed")
      }
    }

  override fun getFacilities() {
    observable.subscribeWith(observer)
  }

  fun formListData(mFacilitiesModel: FacilitiesModel?, facilityType: String): List<Option> {
    val optionList = ArrayList<Option>()
    for (i in 0 until mFacilitiesModel?.facilities?.size.orDefaultInt()) {
      if (mFacilitiesModel?.facilities?.get(i)?.facilityId == facilityType) {
        mFacilitiesModel.facilities?.get(i)?.options?.let { optionList.addAll(it) }
      }
    }
    return optionList
  }

  fun formImageListFortFirstFacilities(apartment: Int = 0, condo: Int,
    boat: Int, land: Int): MutableList<Int>? {
    val imageList = mutableListOf<Int>()
    imageList.add(apartment)
    imageList.add(condo)
    imageList.add(boat)
    imageList.add(land)
    return imageList
  }

  fun formImageListForSecondFacilities(noOfRooms: Int = 0, noRooms: Int): MutableList<Int>? {
    val imageList = mutableListOf<Int>()
    imageList.add(noOfRooms)
    imageList.add(noRooms)
    return imageList
  }

  fun formImageListFortThirdFacilities(swimmingPool: Int = 0, gardenArea: Int,
    garage: Int): MutableList<Int>? {
    val imageList = mutableListOf<Int>()
    imageList.add(swimmingPool)
    imageList.add(gardenArea)
    imageList.add(garage)
    return imageList
  }

}
