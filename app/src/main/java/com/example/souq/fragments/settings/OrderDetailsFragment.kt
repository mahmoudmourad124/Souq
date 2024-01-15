package com.example.souq.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.souq.adapters.BillingProductAdapter
import com.example.souq.data.order.OrderStatus
import com.example.souq.data.order.getOrderStatus
import com.example.souq.databinding.FragmentOrderDetailBinding
import com.example.souq.util.VerticalItemDecoration

class OrderDetailsFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val billingProductsAdapter by lazy { BillingProductAdapter() }
    private val args by navArgs<OrderDetailsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val order = args.order
        setupOrderRV()
        binding.apply {
            tvOrderId.text = "Order # ${order.orderId}"

            stepView.setSteps(
                mutableListOf(
                    OrderStatus.Ordered.status,
                    OrderStatus.Confirmed.status,
                    OrderStatus.Shipped.status,
                    OrderStatus.Delivered.status,
                )
            )

            val currentOrderState = when (getOrderStatus(order.orderStatus)) {
                OrderStatus.Ordered -> 0
                OrderStatus.Confirmed -> 1
                OrderStatus.Shipped -> 2
                OrderStatus.Delivered -> 3
                else -> 0
            }
            stepView.go(currentOrderState, false)
            if (currentOrderState==3)
            {
                stepView.done(true)
            }

            tvFullName.text=order.address.fullName
            tvAddress.text="${order.address.street} ${order.address.city}"
            tvPhoneNumber.text=order.address.phone
            tvTotalPrice.text=order.totalPrice.toString()

        }
        billingProductsAdapter.differ.submitList(order.products)
    }

    private fun setupOrderRV() {
        binding.rvProducts.apply {
            adapter = billingProductsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemDecoration())
        }
    }
}