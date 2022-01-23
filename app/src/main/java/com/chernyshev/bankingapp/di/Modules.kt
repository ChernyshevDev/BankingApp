package com.chernyshev.bankingapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.chernyshev.bankingapp.data.TransactionsUseCaseImpl
import com.chernyshev.bankingapp.data.repository.PinRepositoryImpl
import com.chernyshev.bankingapp.data.repository.UserRepositoryImpl
import com.chernyshev.bankingapp.data.usecase.UserBalanceUseCaseImpl
import com.chernyshev.bankingapp.domain.repository.PinRepository
import com.chernyshev.bankingapp.domain.repository.UserRepository
import com.chernyshev.bankingapp.domain.usecase.TransactionsUseCase
import com.chernyshev.bankingapp.domain.usecase.UserBalanceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

private const val COMMON_PREF_NAME = "common-prefs"
private const val SECURE_PREF_NAME = "secure-prefs"

@Module
@InstallIn(ViewModelComponent::class)
class ProvidersModule {

    @Provides
    @ViewModelScoped
    fun provideUserBalanceUseCase(): UserBalanceUseCase = UserBalanceUseCaseImpl()

    @Provides
    @ViewModelScoped
    fun provideTransactionsUseCase(): TransactionsUseCase = TransactionsUseCaseImpl()

    @Provides
    @ViewModelScoped
    fun providePinRepository(@Named("securePrefs") sharedPreferences: SharedPreferences): PinRepository =
        PinRepositoryImpl(sharedPreferences)

    @Provides
    @ViewModelScoped
    fun provideUserRepository(@Named("commonPrefs") sharedPreferences: SharedPreferences): UserRepository =
        UserRepositoryImpl(sharedPreferences)
}

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    @Named("commonPrefs")
    fun provideSharedPrefs(@ApplicationContext applicationContext: Context): SharedPreferences =
        applicationContext.getSharedPreferences(
            COMMON_PREF_NAME, Context.MODE_PRIVATE
        )

    @Provides
    @Singleton
    @Named("securePrefs")
    fun provideSecureSharedPrefs(@ApplicationContext applicationContext: Context): SharedPreferences {
        val mainKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            applicationContext,
            SECURE_PREF_NAME,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}