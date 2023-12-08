package com.example.souq.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.example.souq.util.Constants.INTRODUCTION_SP
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuel {
    @Provides
    @Singleton
    fun provideFireBaseAuthentication()= FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDataBase()= Firebase.firestore

    @Provides
    fun provideProductionSP(
        application: Application
    )=application.getSharedPreferences(INTRODUCTION_SP,MODE_PRIVATE)

}
