package com.example.beercompapp.di

import android.content.Context
import androidx.room.Room
import com.example.beercompapp.common.Constants
import com.example.beercompapp.common.Constants.PRODUCT_TABLE_NAME
import com.example.beercompapp.data.dao.CartItemDao
import com.example.beercompapp.data.dao.LikesDao
import com.example.beercompapp.data.dao.ProductDao
import com.example.beercompapp.data.dao.UserDao
import com.example.beercompapp.data.db.BeerAppDatabase
import com.example.beercompapp.data.network.ApiClient
import com.example.beercompapp.data.network.BeerApiService
import com.example.beercompapp.data.repository.CartItemRepositoryImpl
import com.example.beercompapp.data.repository.LikesRepositoryImpl
import com.example.beercompapp.data.repository.ProductRepositoryImpl
import com.example.beercompapp.data.repository.UserRepositoryImpl
import com.example.beercompapp.data.settings.AppSettings
import com.example.beercompapp.domain.repository.CartItemRepository
import com.example.beercompapp.domain.repository.LikesRepository
import com.example.beercompapp.domain.repository.ProductRepository
import com.example.beercompapp.domain.repository.UserRepository
import com.example.beercompapp.domain.use_cases.cart_item_usecases.*
import com.example.beercompapp.domain.use_cases.likes_usecases.*
import com.example.beercompapp.domain.use_cases.products_usecases.*
import com.example.beercompapp.domain.use_cases.user_usecases.*
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
    fun provideBeerRepository(api: ApiClient, dao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao): UserRepository {
        return UserRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideLikesRepository(dao: LikesDao): LikesRepository {
        return LikesRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideCartRepository(dao: CartItemDao): CartItemRepository {
        return CartItemRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getBeersFromNetworkUseCase = GetBeersFromNetworkUseCase(repository),
            getSnacksFromNetworkUseCase = GetSnacksFromNetworkUseCase(repository),
            getProductByIdFromDbUseCase = GetProductByIdFromDbUseCase(repository),
            getProductsFromDbUseCase = GetProductsFromDbUseCase(repository),
            updateProductInDbUseCase = UpdateProductInDbUseCase(repository),
            addProductToDBUseCase = AddProductToDBUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideCartUseCases(repository: CartItemRepository): CartUseCases {
        return CartUseCases(
            updateCartItemUseCase = UpdateCartItemUseCase(repository),
            getCartItemsFromDbUseCase = GetCartItemsFromDbUseCase(repository),
            addToCartUseCase = AddToCartUseCase(repository),
            deleteCartItemUseCase = DeleteCartItemUseCase(repository),
            emptyCartUseCase = EmptyCartUseCase(repository),
        )
    }


    @Provides
    @Singleton
    fun provideLikeUseCases(repository: LikesRepository, appSettings: AppSettings): LikesUseCases {
        return LikesUseCases(
            getUserLikesUseCase = GetUserLikesUseCase(repository),
            removeLikeUseCase = RemoveLikeUseCase(repository),
            addLikeUseCase = AddLikeUseCase(repository),
            isItemLikedUseCase = IsItemLikedUseCase(repository),
            likeOrDislikeUseCase = LikeOrDislikeUseCase(appSettings, repository),
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(
        repository: UserRepository,
        appSettings: AppSettings,
        @ApplicationContext context: Context
    ): UserUseCases {
        return UserUseCases(
            addUserUseCase = AddUserUseCase(repository, context),
            deleteUserUseCase = DeleteUserUseCase(repository),
            getUserByNumberUseCase = GetUserByNumberUseCase(repository, context),
            updateUserUseCase = UpdateUserUseCase(repository, context),
            setCurrentUserUseCase = SetCurrentUserUseCase(appSettings),
            getActiveUserUseCase = GetActiveUserUseCase(appSettings)
        )
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): BeerAppDatabase {
        return Room.databaseBuilder(context, BeerAppDatabase::class.java, PRODUCT_TABLE_NAME)
            .build()
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

    @Provides
    @Singleton
    fun providesCartItemDao(database: BeerAppDatabase): CartItemDao {
        return database.cartItemDao()
    }

    @Provides
    @Singleton
    fun providesLikesDao(database: BeerAppDatabase): LikesDao {
        return database.likesDao()
    }
}