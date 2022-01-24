package com.chernyshev.bankingapp.presentation.landing

import com.chernyshev.bankingapp.base.BaseCommand
import com.chernyshev.bankingapp.base.BaseEvent
import com.chernyshev.bankingapp.base.BaseViewState
import com.chernyshev.bankingapp.domain.entity.Balances

data class ViewState(
    val command: Command? = null,
    val isLoadingBalance: Boolean = false
) : BaseViewState

sealed class Event : BaseEvent {
    object OpenedPage : Event()
    object ClickedTransactions : Event()

    data class ReceivedBalances(val balances: Balances) : Event()
    data class ReceivedError(val errorMessage: String?) : Event()
}

sealed class Command : BaseCommand {
    data class SetBalance(val balances: Balances) : Command()
    object NavigateToPin : Command()
    data class ShowError(val errorMessage: String?) : Command()
}