package com.example.beercompapp.data.network

import android.util.Log
import com.example.beercompapp.data.network.dto.BeerDtoList
import com.example.beercompapp.data.network.dto.SnackDtoList
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(private val service: BeerApiService) {

    suspend fun getAllSnacks(): SimpleResponse<SnackDtoList> {
        return safeApiCall { service.getAllSnacks() }
    }

    suspend fun getAllBeer():SimpleResponse<BeerDtoList> {
        return safeApiCall { service.getAllBeer() }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            Log.d("ApiClient", "Response was not successful ${e.message}")
            SimpleResponse.failure(e)
        }
    }
}