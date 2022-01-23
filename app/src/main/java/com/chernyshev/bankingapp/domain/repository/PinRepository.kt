package com.chernyshev.bankingapp.domain.repository

interface PinRepository {
    fun savePin(pin: String)
    fun isPinCorrect(pin: String): Boolean
}