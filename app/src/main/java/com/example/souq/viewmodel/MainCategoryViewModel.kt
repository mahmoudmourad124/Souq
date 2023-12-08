package com.example.souq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.souq.data.Product
import com.example.souq.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,

    ) : ViewModel() {

    private val _specialProducts =
        MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    var specialProduct: StateFlow<Resource<List<Product>>> = _specialProducts


    private val _bestDealsProducts =
        MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    var bestDealsProducts: StateFlow<Resource<List<Product>>> = _bestDealsProducts


    private val _bestProducts =
        MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    var bestProduct: StateFlow<Resource<List<Product>>> = _bestProducts

    //
    private val pagingInfo = PageInfo()

    init {
        fetchSpecialProduct()
        fetchBestDealsProduct()
        fetchBestProduct()
    }


    fun fetchSpecialProduct() {
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }

        firestore.collection("Products")
            .whereEqualTo("category", "Special products").get().addOnSuccessListener { result ->
                val specialProductsList = result.toObjects((Product::class.java))

                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(specialProductsList))

                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Error(it.message.toString()))
                }
            }

    }

    fun fetchBestDealsProduct() {

        viewModelScope.launch {
            _bestDealsProducts.emit(Resource.Loading())
        }

        firestore.collection("Products")
            .whereEqualTo("category", "Best deals").get()
            .addOnSuccessListener { result ->
                val bestDealssList = result.toObjects((Product::class.java))

                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Success(bestDealssList))

                }
                pagingInfo.bestProductPage++
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun fetchBestProduct() {
        if (!pagingInfo.isPagingEnd) {
            viewModelScope.launch {
                _bestProducts.emit(Resource.Loading())
            }

            firestore.collection("Products")
                //ممكن تشيل whereEqualTo
                .whereEqualTo("category", "Best products")
                .orderBy("id",Query.Direction.ASCENDING)
                .limit(pagingInfo.bestProductPage * 6).get().addOnSuccessListener { result ->
                    val bestProducts = result.toObjects((Product::class.java))
//bestProducts == pagingInfo.oldBestProduct check
                    pagingInfo.isPagingEnd = bestProducts == pagingInfo.oldBestProduct
                    pagingInfo.oldBestProduct = bestProducts
                    viewModelScope.launch {
                        _bestProducts.emit(Resource.Success(bestProducts))

                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _bestProducts.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
    }

}

data class PageInfo(
    var bestProductPage: Long = 1,
    var oldBestProduct: List<Product> = emptyList(),
    var isPagingEnd: Boolean = false
)