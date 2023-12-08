package com.example.souq.fragments.loginregester

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.souq.R
import com.example.souq.data.User
import com.example.souq.databinding.FragmentRegestiterBinding
import com.example.souq.util.Resource
import com.example.souq.util.RegisterValidation
import com.example.souq.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_regestiter) {
    private val TAG ="RegisterFragment"
    private lateinit var binding: FragmentRegestiterBinding
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegestiterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //navigation
        binding.souqDontHaveanyacount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


        //snackbar
//       val snackbar= Snackbar.make(binding.root ,"hi", Snackbar.LENGTH_SHORT)
//        snackbar.setAction("Undo"){
//            Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show()
//        }.show()



        binding.apply {
            buttonRegisterRegister.setOnClickListener {
                val user = User(
                    editTextFirstName.text.toString().trim(),
                    editTextLastName.text.toString().trim(),
                    editTextEmail.text.toString().trim()
                )
                val password = editTextPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)

            }
            lifecycleScope.launchWhenStarted {
                viewModel.register.collect {
                    when (it){
                        is Resource.Loading -> {
                            Log.d("test",it.data.toString())
                            binding.buttonRegisterRegister.startAnimation()
                        }
                        is Resource.Error -> binding.buttonRegisterRegister.revertAnimation()
                        is Resource.Success -> {
                            Log.d(TAG,it.message.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }

                        else -> Unit
                    }
                }
            }
            lifecycleScope.launchWhenStarted {
                viewModel.validation.collect{validation->
                    if (validation.email is RegisterValidation.Failed){
                        withContext(Dispatchers.Main){
                            binding.editTextEmail.apply {
                                requestFocus()
                                error=validation.email.message
                            }
                        }
                    }
                    if (validation.password is RegisterValidation.Failed){
                        withContext(Dispatchers.Main){
                            binding.editTextPassword.apply {
                                requestFocus()
                                error=validation.password.message
                            }
                        }
                    }

                }
            }
        }

    }
}