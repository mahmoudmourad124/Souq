package com.example.souq.fragments.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.souq.data.Category
import com.example.souq.util.Resource
import com.example.souq.viewmodel.CategoryViewmodel
import com.example.souq.viewmodel.factory.BaseCategoryViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AccessoryFragment : BaseCategoryFragment() {
    @Inject

    lateinit var firestore: FirebaseFirestore
    val viewmodel by viewModels<CategoryViewmodel> {
        BaseCategoryViewModelFactory(firestore, Category.Accessory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewmodel.offerProduct.collectLatest {
                when (it) {
                    is Resource.Loading -> showOfferLoding()
                    ////note7
                    //شوف ايه موضوعو differ و lists
                    is Resource.Success -> {
                        offerAdapter.differ.submitList(it.data)
                        hideOfferLoading()
                    }

                    is Resource.Error -> {
                        Snackbar.make(
                            requireView(),
                            it.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        hideOfferLoading()
                    }

                    is Resource.Unspecified -> TODO()
                }
            }
            viewmodel.bestProduct.collectLatest {
                when (it) {
                    is Resource.Loading -> showBestProductLoding()
                    ////note7
                    //شوف ايه موضوعو differ و lists
                    is Resource.Success -> {
                        bestProductsAdapter.differ.submitList(it.data)
                        hideBestProductLoading()
                    }

                    is Resource.Error -> {
                        Snackbar.make(
                            requireView(),
                            it.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        hideBestProductLoading()
                    }

                    is Resource.Unspecified -> TODO()
                }
            }
        }
    }

    override fun onBestProductsPaggingRequest() {
        super.onBestProductsPaggingRequest()
    }

    override fun onOfferProductsPaggingRequest() {
        super.onOfferProductsPaggingRequest()
    }
}