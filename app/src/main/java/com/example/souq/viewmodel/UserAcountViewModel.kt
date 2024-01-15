package com.example.souq.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.souq.SouqApplication
import com.example.souq.data.User
import com.example.souq.util.RegisterValidation
import com.example.souq.util.Resource
import com.example.souq.util.validateEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UserAcountViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: StorageReference,
    app: Application
) : AndroidViewModel(app) {
    private val _user = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val user = _user.asStateFlow()

    private val _updateInfo = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val updateInfo = _updateInfo.asStateFlow()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch { _user.emit(Resource.Loading()) }
        firestore.collection("user").document(auth.uid!!).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                user?.let {
                    viewModelScope.launch {
                        _user.emit(Resource.Success(it))
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _user.emit(Resource.Error(it.message.toString()))
                }

            }
    }

     fun updateUser(user: User, imageUri: Uri?) {
        val areInputsValid = validateEmail(user.email) is RegisterValidation.Sucess
                && user.firstName.trim().isNotEmpty()
                && user.lastName.trim().isNotEmpty()
        if (!areInputsValid) {
            viewModelScope.launch {
                _user.emit(Resource.Error("Check your inputs"))
            }
            return
        }
        viewModelScope.launch {
            _updateInfo.emit(Resource.Loading())
        }
        if (imageUri == null) {
            saveUserInfo(user, true)
        } else
            saveUserInfoWithImage(user, imageUri)

    }

    private fun saveUserInfoWithImage(user: User, imageUri: Uri) {
       viewModelScope.launch {
           try {

               val imageBitMap= MediaStore.Images.Media.getBitmap(
                   getApplication<SouqApplication>().contentResolver,
                   imageUri
               )
               val byteArrayOutputStream=ByteArrayOutputStream()
               imageBitMap.compress(Bitmap.CompressFormat.JPEG,96,byteArrayOutputStream)
               val imageByteArray=byteArrayOutputStream.toByteArray()

               val imageDirectroy=
                   storage.child("profileImages/${auth.uid}/${UUID.randomUUID()}")
               val result =imageDirectroy.putBytes(imageByteArray).await()
               val imageUrl=result.storage.downloadUrl.await().toString()
               saveUserInfo(user.copy(imagePath = imageUrl),false)


           }catch (e:Exception){

               viewModelScope.launch {
                   _updateInfo.emit(Resource.Error(e.message.toString()))
               }
           }
       }
    }

    private fun saveUserInfo(user: User, shouldRetrievedOldImage: Boolean) {
        firestore.runTransaction { transition ->
            val documentRef = firestore.collection("user").document(auth.uid!!)
            if (shouldRetrievedOldImage) {
                val currentUser = transition.get(documentRef).toObject(User::class.java)
                val newUser = user.copy(imagePath = currentUser?.imagePath ?: "")
                transition.set(documentRef, newUser)
            } else {
                transition.set(documentRef, user)
            }

        }.addOnSuccessListener {
           viewModelScope.launch {
               _updateInfo.emit(Resource.Success(user))
           }
        }.addOnFailureListener {
            viewModelScope.launch {
                _updateInfo.emit(Resource.Error(it.message.toString()))
            }
        }
    }

}