package com.chernyshev.bankingapp.presentation.transactions

import com.chernyshev.bankingapp.base.BaseCommand
import com.chernyshev.bankingapp.base.BaseEvent
import com.chernyshev.bankingapp.base.BaseViewState
import com.chernyshev.bankingapp.domain.entity.Transaction

data class ViewState(
    val command: Command? = null,
    val isLoading: Boolean = false
) : BaseViewState

sealed class Event : BaseEvent {
    object PageOpened : Event()
    data class ReceivedTransactions(val transactions: List<Transaction>) : Event()
    data class ReceivedError(val errorMessage: String?) : Event()
}

sealed class Command : BaseCommand {
    data class SetTransactions(val transactions: List<Transaction>, val transactionsSum: String) : Command()
    data class DisplayError(val errorMessage: String?) : Command()
}