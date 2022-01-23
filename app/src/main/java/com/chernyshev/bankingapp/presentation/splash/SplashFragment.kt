package com.chernyshev.bankingapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.chernyshev.bankingapp.NavGraphDirections
import com.chernyshev.bankingapp.base.BaseFragment
import com.chernyshev.bankingapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {
    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performFakeLoading()
    }

    override fun initObservers() {
        viewModel.subscribeToCommand(viewLifecycleOwner) { command ->
            when (command) {
                Command.NavigateToCreatePin -> navController.navigate(
                    NavGraphDirections.actionGlobalPinFragment(
                        true
                    )
                )
                Command.NavigateToLanding -> navController.navigate(SplashFragmentDirections.navigateToLanding())
            }
        }
    }

    private fun performFakeLoading() {
        MainScope().launch {
            delay(2000)
            viewModel.sendEvent(Event.FinishedFakeLoading)
        }
    }
}