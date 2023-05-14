package com.example.beercompapp.domain.use_cases

import android.util.Log
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductAppRepository
import javax.inject.Inject

class UpdateProductInDbUseCase @Inject constructor(
    private val repository: ProductAppRepository
) {

    suspend operator fun invoke(item: ProductItem) {
        val newItem = item.copy(
            isFavorite = !item.isFavorite
        )
        Log.d("UpdProdInDbUC", newItem.isFavorite.toString())
        return repository.updateProduct(newItem)
    }
}