package com.example.souq.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.souq.R
import com.example.souq.databinding.FragmentProfileBinding
import com.example.souq.databinding.FragmentSearchBinding

class ProfileFragment : Fragment() {


    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

}