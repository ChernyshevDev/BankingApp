package com.chernyshev.bankingapp.presentation.pin

import com.chernyshev.bankingapp.base.BaseViewModel
import com.chernyshev.bankingapp.domain.repository.PinRepository
import com.chernyshev.bankingapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val pinRepository: PinRepository,
    private val userRepository: UserRepository
) : BaseViewModel<ViewState, Event, Command>(ViewState()) {
    override fun onReduceState(event: Event): ViewState {
        return when (event) {
            is Event.EnteredPin -> when (state.currentStep) {
                PageStep.EnterPin -> {
                    val newCommand = if (pinRepository.isPinCorrect(event.pin)) {
                        Command.NavigateToTransactions
                    } else {
                        Command.ShowError(ErrorType.IncorrectPin)
                    }
                    state.copy(command = newCommand)
                }
                PageStep.CreatePin -> state.copy(
                    pin = event.pin,
                    currentStep = PageStep.ConfirmPin,
                    command = Command.UpdateViews(PageStep.ConfirmPin)
                )
                PageStep.ConfirmPin -> if (event.pin == state.pin) {
                    pinRepository.savePin(event.pin)
                    userRepository.isFirstTimeUser = false
                    state.copy(command = Command.NavigateToLanding)
                } else {
                    state.copy(command = Command.ShowError(ErrorType.DifferentPins))
                }
                null -> {
                    // should not happen
                    state
                }
            }
            is Event.ReceivedArgs -> {
                val newCurrentStep = if (event.args.isCreatePin) {
                    PageStep.CreatePin
                } else {
                    PageStep.EnterPin
                }
                state.copy(
                    currentStep = newCurrentStep,
                    command = Command.UpdateViews(newCurrentStep)
                )
            }
        }
    }

    override fun ViewState.clearCommand(): ViewState = this.copy(command = null)
    override val ViewState.command: Command?
        get() = this.command
}