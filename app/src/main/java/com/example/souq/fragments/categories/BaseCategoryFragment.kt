package com.example.souq.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.souq.adapters.BestProductsAdapter
import com.example.souq.databinding.FragmentBaseCategoryBinding

open class BaseCategoryFragment : Fragment() {

    private lateinit var binding: FragmentBaseCategoryBinding

    //protected
    protected val offerAdapter: BestProductsAdapter by lazy { BestProductsAdapter() }
    protected val bestProductsAdapter: BestProductsAdapter by lazy { BestProductsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOfferRv()
        setupBestProductRv()
        //???????? note1
        binding.rvOfferProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //???????
                if (!recyclerView.canScrollVertically(1) && dx != 0) {
                    onOfferProductsPaggingRequest()
                }
            }
        })
        //???????? note2

        binding.nestedScrollBaseCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY) {
                onBestProductsPaggingRequest()
            }
        })
    }

    fun showOfferLoding() {
        binding.offerProductsProgressBar.visibility = View.VISIBLE
    }

    fun hideOfferLoading() {
        binding.offerProductsProgressBar.visibility = View.GONE

    }

    fun showBestProductLoding() {
        binding.bestProductsProgressBar.visibility = View.VISIBLE
    }

    fun hideBestProductLoading() {
        binding.bestProductsProgressBar.visibility = View.GONE

    }

    open fun onBestProductsPaggingRequest() {

    }

    open fun onOfferProductsPaggingRequest() {


    }

    private fun setupBestProductRv() {
     //trash   val itemMargin= BestProductsRVMargins()
        binding.rvBestProducts.apply {
          //trash  addItemDecoration(itemMargin)
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = bestProductsAdapter
        }
    }

    private fun setupOfferRv() {
        binding.rvOfferProducts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }

    }


}