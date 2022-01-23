package com.chernyshev.bankingapp.data.repository

import android.content.SharedPreferences
import com.chernyshev.bankingapp.domain.repository.PinRepository

private const val KEY_PIN = "pin"

class PinRepositoryImpl(
    private val securePrefs: SharedPreferences
) : PinRepository {
    private var pinCode: String
        get() = securePrefs.getString(KEY_PIN, "") ?: ""
        set(value) {
            securePrefs.edit().putString(KEY_PIN, value).apply()
        }

    override fun savePin(pin: String) {
        pinCode = pin
    }

    override fun isPinCorrect(pin: String) = pin == pinCode
}