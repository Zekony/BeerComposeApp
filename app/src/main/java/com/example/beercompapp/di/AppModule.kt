package com.example.beercompapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.beercompapp.common.Constants
import com.example.beercompapp.data.db.BeerAppDatabase
import com.example.beercompapp.data.db.ProductDao
import com.example.beercompapp.data.db.UserDao
import com.example.beercompapp.data.network.ApiClient
import com.example.beercompapp.data.network.BeerApiService
import com.example.beercompapp.data.repository.ProductRepositoryImpl
import com.example.beercompapp.data.repository.UserRepositoryImpl
import com.example.beercompapp.domain.repository.ProductAppRepository
import com.example.beercompapp.domain.repository.UserRepository
import com.example.beercompapp.domain.use_cases.*
import com.example.beercompapp.domain.use_cases.user_use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideBeerRepository(api: ApiClient, dao: ProductDao): ProductAppRepository {
        return ProductRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao): UserRepository {
        return UserRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: ProductAppRepository): ProductUseCases {
        return ProductUseCases(
            getBeersFromNetworkUseCase = GetBeersFromNetworkUseCase(repository),
            getSnacksFromNetworkUseCase = GetSnacksFromNetworkUseCase(repository),
            getProductByIdFromDbUseCase = GetProductByIdFromDbUseCase(repository),
            getProductsFromDbUseCase = GetProductsFromDbUseCase(repository),
            updateProductInDbUseCase = UpdateProductInDbUseCase(repository),
            updateCartItemUseCase = UpdateCartItemUseCase(repository),
            getCartItemsFromDbUseCase = GetCartItemsFromDbUseCase(repository),
            addToCartUseCase = AddToCartUseCase(repository),
            deleteCartItemUseCase = DeleteCartItemUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            addUserUseCase = AddUserUseCase(repository),
            deleteUserUseCase = DeleteUserUseCase(repository),
            getUserByIdUseCase = GetUserByIdUseCase(repository),
            updateUserUseCase = UpdateUserUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): BeerAppDatabase {
        return Room.databaseBuilder(context, BeerAppDatabase::class.java, "ProductTable").build()
    }

    @Provides
    @Singleton
    fun providesProductDao(database: BeerAppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun providesUserDao(database: BeerAppDatabase): UserDao {
        return database.userDao()
    }
}