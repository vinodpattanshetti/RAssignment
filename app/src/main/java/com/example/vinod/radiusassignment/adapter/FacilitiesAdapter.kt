package com.example.vinod.radiusassignment.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton

import com.example.vinod.radiusassignment.R
import com.example.vinod.radiusassignment.models.Option
import com.example.vinod.radiusassignment.models.SelectedFacility
import com.example.vinod.radiusassignment.utils.orDefaultInt

class FacilitiesAdapter(
  private val optionList: List<Option>,
  private val adapterType: String,
  private val iActivityCommunicator: IActivityCommunicator, private val imageList: MutableList<Int>?
) : RecyclerView.Adapter<FacilitiesAdapter.DataViewHolder>() {
  private var mSelectedFacility: SelectedFacility? = null
  private var lastSelectedPosition = -1

  override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FacilitiesAdapter.DataViewHolder {
    val view =
      LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_layout, viewGroup, false)
    return DataViewHolder(view)
  }

  override fun onBindViewHolder(holder: FacilitiesAdapter.DataViewHolder, i: Int) {
    holder.mRadioButton.text = optionList[i].name
    holder.mImageView.setImageResource(imageList?.get(i).orDefaultInt())
    holder.mRadioButton.isChecked = lastSelectedPosition == i
  }

  override fun getItemCount(): Int {
    return optionList.size
  }

  inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mRadioButton: RadioButton = itemView.findViewById(R.id.rb_radio_button)
    var mImageView: ImageView = itemView.findViewById(R.id.iv_image)

    init {
      mRadioButton.setOnClickListener {
        mSelectedFacility = SelectedFacility()
        mSelectedFacility?.facilityType = adapterType
        mSelectedFacility?.facilityName = optionList[adapterPosition].name
        mSelectedFacility?.facilityId = optionList[adapterPosition].id
        iActivityCommunicator.sendSelectedFacility(mSelectedFacility)
        lastSelectedPosition = adapterPosition
        notifyDataSetChanged()
      }
    }
  }

  interface IActivityCommunicator {
    fun sendSelectedFacility(mSelectedFacility: SelectedFacility?)
  }

  companion object {
    var PROPERTY_TYPE = "1"
    var NUMBER_OF_ROOMS = "2"
    var OTHER_FACILITIES = "3"
  }
}
