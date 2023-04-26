package com.example.beercompapp.data.repository

import android.util.Log
import com.example.beercompapp.data.network.ApiClient
import com.example.beercompapp.data.network.dto.BeerDtoList
import com.example.beercompapp.data.network.dto.SnackDtoList
import com.example.beercompapp.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val api: ApiClient
): BeerRepository {

    override suspend fun getBeersApi(): BeerDtoList? {
        val request = api.getAllBeer()

        if (request.failed) {
            Log.d("Repository", "Beer Request has failed! ${request.exception?.message}")
            return null
        }
        if (!request.isSuccessful) {
            Log.d("Repository", "Beer Request was not successful!")
            return null
        }

        if (request.isSuccessful) {
            Log.d("Repository", "Beer Request was successful!")
            if (request.body.data.isEmpty()) {
                Log.d("Repository", "Beer Request was successful, but list is empty!")
            }
            return request.body
        }
        return null
    }

    override suspend fun getSnacksApi(): SnackDtoList? {
        val request = api.getAllSnacks()

        if (request.failed) {
            Log.d("Repository", "Snack Request has failed! ${request.exception?.message}")
            return null
        }
        if (!request.isSuccessful) {
            Log.d("Repository", "Snack Request was not successful!")
            return null
        }

        if (request.isSuccessful) {
            Log.d("Repository", "Snack Request was successful!")
            if (request.body.data.isEmpty()) {
                Log.d("Repository", "Snack Request was successful, but list is empty!")
            }
            return request.body
        }
        return null
    }
}