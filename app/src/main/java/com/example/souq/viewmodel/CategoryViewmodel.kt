package com.example.souq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.souq.data.Category
import com.example.souq.data.Product
import com.example.souq.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//???????? note3

//we can't pass data with Dager Hilt we will create our factory

class CategoryViewmodel constructor(
    private val firestore: FirebaseFirestore,
    private val category: Category
) : ViewModel() {
    private val _offerProduct = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val offerProduct = _offerProduct.asStateFlow()

    private val _bestProduct = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProduct = _bestProduct.asStateFlow()

    init {
        fetchBestProduct()
        fetchOfferProducts()
    }

    fun fetchOfferProducts() {
        viewModelScope.launch {
            _offerProduct.emit(Resource.Loading())
        }
        firestore.collection("Products").whereEqualTo("category", category.category)
            .whereNotEqualTo("offerPercentage", null)
//            .limit()
            .get()
            .addOnSuccessListener {
                val products = it.toObjects(Product::class.java)
                viewModelScope.launch {
                    _offerProduct.emit(Resource.Success(products))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _offerProduct.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun fetchBestProduct() {
        firestore.collection("Products").whereEqualTo("category", category.category)
             .whereNotEqualTo("offerPercentage",null)
            .get().addOnSuccessListener {
                val products = it.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestProduct.emit(Resource.Success(products))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestProduct.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}