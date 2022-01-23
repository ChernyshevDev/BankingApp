package com.chernyshev.bankingapp.presentation.splash

import com.chernyshev.bankingapp.base.BaseCommand
import com.chernyshev.bankingapp.base.BaseEvent
import com.chernyshev.bankingapp.base.BaseViewState

data class ViewState(
    val command: Command? = null
) : BaseViewState

sealed class Event : BaseEvent {
    object FinishedFakeLoading : Event()
}

sealed class Command : BaseCommand {
    object NavigateToCreatePin : Command()
    object NavigateToLanding : Command()
}