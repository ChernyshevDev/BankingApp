package com.chernyshev.bankingapp.presentation.splash

import com.chernyshev.bankingapp.base.BaseViewModel
import com.chernyshev.bankingapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<ViewState, Event, Command>(ViewState()) {
    override fun onReduceState(event: Event): ViewState {
        return when (event) {
            Event.FinishedFakeLoading -> state.copy(
                command = if (userRepository.isFirstTimeUser) {
                    Command.NavigateToCreatePin
                } else {
                    Command.NavigateToLanding
                }
            )
        }
    }

    override fun ViewState.clearCommand(): ViewState = this.copy(command = null)
    override val ViewState.command: Command?
        get() = this.command
}