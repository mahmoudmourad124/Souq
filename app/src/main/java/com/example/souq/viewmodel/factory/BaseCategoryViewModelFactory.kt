package com.example.souq.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.souq.data.Category
import com.example.souq.viewmodel.CategoryViewmodel
import com.google.firebase.firestore.FirebaseFirestore

class BaseCategoryViewModelFactory(
    private val firestore: FirebaseFirestore,
    private val category: Category
): ViewModelProvider.Factory {
    //???????? note6
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewmodel(firestore,category)as T
    }
}