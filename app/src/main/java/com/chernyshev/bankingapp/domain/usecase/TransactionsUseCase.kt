package com.chernyshev.bankingapp.domain.usecase

import com.chernyshev.bankingapp.base.OperationResult
import com.chernyshev.bankingapp.domain.entity.Transaction

interface TransactionsUseCase {
    suspend fun getTransactions(): OperationResult<List<Transaction>>
}