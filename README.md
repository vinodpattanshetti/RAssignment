# RAssignment
RadisAssignment Application

This is a demo app on hot to implement list facilities on recycler view list using MVP architecture, Live Data, retrofit, Rxjava, Room database, in Kotlin language.

Step 1:
 *
 Created new android project with FacilityListActivity page to show list of facilities from api.
Implemented expected UI as per requirement in FacilityListActivity.class

 *
 Implemented retrofit call using below class
 ```
class NetworkClient {
 companion object {
  private
  var retrofit: Retrofit ? = null
  fun getRetrofit(): Retrofit ? {
   if (retrofit == null) {
    val builder = OkHttpClient.Builder()
    val okHttpClient = builder.build()
    retrofit = Retrofit.Builder().baseUrl("https://my-json-server.typicode.com/")
     .addConverterFactory(GsonConverterFactory.create())
     .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(okHttpClient).build()
   }
   return retrofit
  }
 }
}
```

Step 2:
 *
 After calling api, the fetched data is stored in room data base
 ```
@Database(entities = [FacilitiesModel::class], version = 2, exportSchema = false)

@TypeConverters(DataConverter::class)
abstract class FacilityDatabase: RoomDatabase() {
 abstract fun getIFacilityDao(): FacilityDao
 private
 var INSTANCE: FacilityDatabase ? = null
 fun getDatabase(context: Context): FacilityDatabase ? {
  if (INSTANCE == null) {
   synchronized(FacilityDatabase::class.java) {
    if (INSTANCE == null) {
     INSTANCE = Room.databaseBuilder(
      context.applicationContext, FacilityDatabase::class.java, "facility_database"
     ).build()
    }
   }
  }
  return INSTANCE
 }
}
```

Step 3:
 *
 Once we have the data available in Room database, we are not calling the facilities api again
for 24 hours
 *
 If Room data base doesn’ t have the facilities data and last api call was before 24 hours then we are calling api
again.In short we refreshing room database on daily basis
```
private fun getFacilitiesFromDatabase() {
 mFacilityRepository ? .getFacilityModel() ?
  .observe(this, Observer < FacilitiesModel > {
   mFacilitiesModel ->
   mBinder ? .pbProgress ? .hideView()
   if (callApiAfterOneDay()) {
    mBinder ? .pbProgress ? .showView()
    mMainPresenter ? .getFacilities()
   } else if (null != mFacilitiesModel && mFacilitiesModel.facilities.isNullOrEmpty() && mFacilitiesModel.exclusions.isNullOrEmpty()) {
    mBinder ? .pbProgress ? .showView()
    mMainPresenter ? .getFacilities()
   } else {
    initAdapter(mFacilitiesModel)
   }
  })
}
```

Step 4:
 Once user selects any combination from three facilities and
if selected facilities are have below exclusion combination
then we are simply showing“ Select Proper combination” on click of Submit the Result

Exclusion from selected Facilities -> {
```
 4,
 6
} {
 3,
 12
} {
 7,
 12
}
```

Step 5:
 If selected facilities combination is correct and it passes exclusion check then we are showing the selected facilities
on SelectedResultDetailActivity.class page.

And that 's it!
