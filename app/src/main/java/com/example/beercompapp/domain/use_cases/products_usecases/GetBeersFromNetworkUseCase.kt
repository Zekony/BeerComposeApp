package com.example.beercompapp.domain.use_cases.products_usecases

import android.util.Log
import com.example.beercompapp.common.Resource
import com.example.beercompapp.data.network.dto.toProductItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBeersFromNetworkUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<ProductItem>>> = flow {
        try {
            emit(Resource.Loading())
            Log.d("BeerUseCase", "Beer Request is called")
            val product = repository.getBeersApi()?.data?.map { it.toProductItem() }
            emit(Resource.Success(product))
            if (product == null || product.isEmpty()) {
            emit(Resource.Error("An unexpected error occurred"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            Log.d("BeerUseCase", "Beer Request returned HttpException")
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Please check your internet connection"))
            Log.d("BeerUseCase", "Beer Request returned IOException")
        }
    }
}