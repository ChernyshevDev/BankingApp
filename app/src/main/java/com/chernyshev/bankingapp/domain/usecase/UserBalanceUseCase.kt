package com.chernyshev.bankingapp.domain.usecase

import com.chernyshev.bankingapp.base.OperationResult
import com.chernyshev.bankingapp.domain.entity.Balances

interface UserBalanceUseCase {
    suspend fun getBalances(): OperationResult<Balances>
}