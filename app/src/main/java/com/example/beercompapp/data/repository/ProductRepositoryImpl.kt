package com.example.beercompapp.data.repository

import android.util.Log
import com.example.beercompapp.data.db.ProductDao
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.network.ApiClient
import com.example.beercompapp.data.network.dto.BeerDtoList
import com.example.beercompapp.data.network.dto.SnackDtoList
import com.example.beercompapp.domain.repository.ProductAppRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiClient,
    private val dao: ProductDao
) : ProductAppRepository {

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

    override fun getProducts() = dao.getProducts()

    override fun getProductById(id: String) = dao.getProductById(id)
    override suspend fun addProduct(item: ProductItem) {
        dao.addProduct(item)
    }
    override suspend fun updateProduct(item: ProductItem) {
        dao.updateProduct(item)
    }

    //Cart functions
    override fun getCartItems() = dao.getCartItems()

    override suspend fun addToCart(item: CartItem) {
        dao.addToCart(item)
    }
    override suspend fun updateCartItem(item: CartItem) {
        dao.updateCartItem(item)
    }

    override suspend fun deleteCartItem(item: CartItem) {
        dao.deleteCartItem(item)
    }


}