package com.chernyshev.bankingapp.data.usecase

import com.chernyshev.bankingapp.base.OperationResult
import com.chernyshev.bankingapp.domain.entity.Balances
import com.chernyshev.bankingapp.domain.usecase.UserBalanceUseCase
import kotlinx.coroutines.delay

class UserBalanceUseCaseImpl : UserBalanceUseCase {
    override suspend fun getBalances(): OperationResult<Balances> {
        performFakeLoading()
        return OperationResult.ResultSuccess(Balances("3249.48", "6653.48"))
    }

    private suspend fun performFakeLoading() = delay(1500)
}