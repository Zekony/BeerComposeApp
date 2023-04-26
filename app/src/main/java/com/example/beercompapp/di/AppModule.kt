package com.example.beercompapp.di

import com.example.beercompapp.common.Constants
import com.example.beercompapp.data.network.ApiClient
import com.example.beercompapp.data.network.BeerApiService
import com.example.beercompapp.data.repository.BeerRepositoryImpl
import com.example.beercompapp.domain.repository.BeerRepository
import com.example.beercompapp.domain.use_cases.GetBeersUseCase
import com.example.beercompapp.domain.use_cases.GetSnacksUseCase
import com.example.beercompapp.domain.use_cases.ProductUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): BeerApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeerApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesApiClient(apiService: BeerApiService): ApiClient {
        return ApiClient(apiService)
    }

    @Provides
    @Singleton
    fun provideBeerRepository(api: ApiClient): BeerRepository {
        return BeerRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: BeerRepository): ProductUseCases {
        return ProductUseCases(
            getBeersUseCase = GetBeersUseCase(repository),
            getSnacksUseCase = GetSnacksUseCase(repository)
        )
    }
}