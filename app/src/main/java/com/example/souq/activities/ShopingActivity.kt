package com.example.souq.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.souq.R
import com.example.souq.databinding.ActivityShopingBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class ShopingActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityShopingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navController=findNavController(R.id.shopingHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}