package com.chernyshev.bankingapp.domain.usecase

import com.chernyshev.bankingapp.base.OperationResult

interface UserBalanceUseCase {
    suspend fun getBalances(): OperationResult<Balances>
}

data class Balances(val debitBalance: String, val creditBalance: String)