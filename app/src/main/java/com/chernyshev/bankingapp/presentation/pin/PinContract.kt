package com.chernyshev.bankingapp.presentation.pin

import androidx.annotation.StringRes
import com.chernyshev.bankingapp.R
import com.chernyshev.bankingapp.base.BaseCommand
import com.chernyshev.bankingapp.base.BaseEvent
import com.chernyshev.bankingapp.base.BaseViewState

data class ViewState(
    val command: Command? = null,
    val currentStep: PageStep? = null,
    val pin: String = ""
) : BaseViewState

sealed class Event : BaseEvent {
    data class ReceivedArgs(val args: PinFragmentArgs) : Event()
    data class EnteredPin(val pin: String) : Event()
}

sealed class Command : BaseCommand {
    data class UpdateViews(val currentStep: PageStep) : Command()
    object NavigateToTransactions : Command()
    object NavigateToLanding : Command()
    data class ShowError(val errorType: ErrorType) : Command()
}

enum class PageStep(@StringRes val titleResId: Int) {
    EnterPin(R.string.enter_pin),
    CreatePin(R.string.create_pin),
    ConfirmPin(R.string.confirm_pin)
}

enum class ErrorType(@StringRes val errorMessageId: Int) {
    IncorrectPin(R.string.incorrect_pin),
    DifferentPins(R.string.pins_are_different)
}