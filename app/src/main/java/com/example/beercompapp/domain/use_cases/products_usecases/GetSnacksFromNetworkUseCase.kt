package com.example.beercompapp.domain.use_cases.products_usecases

import android.util.Log
import com.example.beercompapp.common.Resource
import com.example.beercompapp.data.network.dto.toProductItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductAppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSnacksFromNetworkUseCase @Inject constructor(
    private val repository: ProductAppRepository
) {
    operator fun invoke(): Flow<Resource<List<ProductItem>>> = flow {
        try {
            emit(Resource.Loading())
            val product = repository.getSnacksApi()?.data?.map { it.toProductItem() }
            if (product == emptyList<ProductItem>()) {
                Log.d("GetSnacksUseCase", "List of snacks returned empty")
            }
            if (product == null) {
                emit(Resource.Error("An unexpected error occurred"))
            }
            emit(Resource.Success(product))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            Log.d("SnackUseCase", "Beer Request returned error")
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Please check your internet connection"))
            Log.d("SnackUseCase", "Beer Request returned error")
        }
    }
}