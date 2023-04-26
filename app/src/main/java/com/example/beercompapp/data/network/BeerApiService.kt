package com.example.beercompapp.data.network

import com.example.beercompapp.data.network.dto.BeerDto
import com.example.beercompapp.data.network.dto.BeerDtoList
import com.example.beercompapp.data.network.dto.SnackDto
import com.example.beercompapp.data.network.dto.SnackDtoList
import retrofit2.Response
import retrofit2.http.*

interface BeerApiService {
    @GET("api/beverages")
    suspend fun getAllBeer(): Response<BeerDtoList>

    @GET("api/snacks")
    suspend fun getAllSnacks(): Response<SnackDtoList>

    @GET("api/beverages/{beerId}")
    suspend fun getBeerById(@Path("beerId") beerId: String): Response<BeerDto>

    @GET("api/snacks/{snackId}")
    suspend fun getSnackById(@Path("snackId") snackId: String): Response<SnackDto>

/*    @POST("snacks/add-snack")
    suspend fun addSnack(@Body snackRequest: SnackRequest): Response<SnackResponse>

    @DELETE("snacks/{snackId}")
    suspend fun deleteSnackById(@Path("snackId") snackId: String ): Response<GetSnackById>*/
}
