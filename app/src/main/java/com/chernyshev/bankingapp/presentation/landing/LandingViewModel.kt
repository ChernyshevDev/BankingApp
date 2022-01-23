package com.chernyshev.bankingapp.presentation.landing

import com.chernyshev.bankingapp.base.BaseViewModel
import com.chernyshev.bankingapp.domain.usecase.UserBalanceUseCase
import com.chernyshev.bankingapp.utils.extensions.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val userBalanceUseCase: UserBalanceUseCase
) : BaseViewModel<ViewState, Event, Command>(ViewState()) {
    override fun onReduceState(event: Event): ViewState {
        return when (event) {
            Event.OpenedPage -> {
                launch {
                    fetchUserBalance()
                }
                state.copy(isLoadingBalance = true)
            }
            Event.ClickedTransactions -> state.copy(command = Command.NavigateToPin)
            is Event.ReceivedError -> state.copy(
                isLoadingBalance = false,
                command = Command.ShowError(event.errorMessage)
            )
            is Event.ReceivedBalances -> state.copy(
                isLoadingBalance = false,
                command = Command.SetBalance(event.balances)
            )
        }
    }

    private suspend fun fetchUserBalance() {
        userBalanceUseCase.getBalances()
            .onSuccess {
                sendEvent(Event.ReceivedBalances(it))
            }
            .onError {
                sendEvent(Event.ReceivedError(it))
            }
    }

    override fun ViewState.clearCommand(): ViewState = this.copy(command = null)
    override val ViewState.command: Command?
        get() = this.command
}