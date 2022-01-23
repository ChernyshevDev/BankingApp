package com.chernyshev.bankingapp.data.repository

import android.content.SharedPreferences
import com.chernyshev.bankingapp.domain.repository.UserRepository
import javax.inject.Inject

private const val KEY_FIRST_TIME_USER = "first_time"

class UserRepositoryImpl @Inject constructor(
    private val commonPrefs: SharedPreferences
) : UserRepository {
    override var isFirstTimeUser: Boolean
        get() = commonPrefs.getBoolean(KEY_FIRST_TIME_USER, true)
        set(value) {
            commonPrefs.edit().putBoolean(KEY_FIRST_TIME_USER, value).apply()
        }
}