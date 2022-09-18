package com.oluwafemi.exhibit.data.network

import com.oluwafemi.exhibit.data.Exhibit
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("Reyst/exhibit_db/list")
    suspend fun getExhibitList(): Response<List<Exhibit>>
}