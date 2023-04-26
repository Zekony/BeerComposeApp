package com.example.beercompapp.domain.repository

import com.example.beercompapp.data.network.dto.BeerDtoList
import com.example.beercompapp.data.network.dto.SnackDtoList

interface BeerRepository {

    suspend fun getBeersApi(): BeerDtoList?

    suspend fun getSnacksApi(): SnackDtoList?

}