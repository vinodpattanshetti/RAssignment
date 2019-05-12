package com.example.vinod.radiusassignment.network

import com.example.vinod.radiusassignment.models.FacilitiesModel

import retrofit2.http.GET
import io.reactivex.Observable

//https://my-json-server.typicode.com/iranjith4/ad-assignment/db

interface NetworkInterface {
  @get:GET("iranjith4/ad-assignment/db") val facilities: Observable<FacilitiesModel>
}
