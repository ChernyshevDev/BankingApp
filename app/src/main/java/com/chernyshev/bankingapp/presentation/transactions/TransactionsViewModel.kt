package com.chernyshev.bankingapp.presentation.transactions

import com.chernyshev.bankingapp.base.BaseViewModel
import com.chernyshev.bankingapp.base.OperationResult
import com.chernyshev.bankingapp.domain.entity.Transaction
import com.chernyshev.bankingapp.domain.usecase.TransactionsUseCase
import com.chernyshev.bankingapp.utils.extensions.launch
import com.chernyshev.bankingapp.utils.extensions.tryToDouble
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionsUseCase: TransactionsUseCase
) : BaseViewModel<ViewState, Event, Command>(ViewState()) {
    override fun onReduceState(event: Event): ViewState {
        return when (event) {
            Event.PageOpened -> {
                launch {
                    fetchTransactions()
                }
                state.copy(isLoading = true)
            }
            is Event.ReceivedError -> state.copy(
                isLoading = false,
                command = Command.DisplayError(event.errorMessage)
            )
            is Event.ReceivedTransactions -> state.copy(
                isLoading = false,
                command = Command.SetTransactions(event.transactions, event.transactions.sum())
            )
        }
    }

    private suspend fun fetchTransactions() {
        when (val result = transactionsUseCase.getTransactions()) {
            is OperationResult.ResultSuccess -> sendEvent(Event.ReceivedTransactions(result.result))
            is OperationResult.ResultError -> sendEvent(Event.ReceivedError(result.error))
        }
    }

    override fun ViewState.clearCommand(): ViewState = this.copy(command = null)
    override val ViewState.command: Command?
        get() = this.command
}

fun List<Transaction>.sum(): String {
    var sum = 0.0
    this.forEach {
        sum += it.amount.tryToDouble()
    }
    return sum.toString()
}