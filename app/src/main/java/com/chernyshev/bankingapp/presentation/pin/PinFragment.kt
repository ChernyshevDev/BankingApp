package com.chernyshev.bankingapp.presentation.pin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.chernyshev.bankingapp.R
import com.chernyshev.bankingapp.base.BaseFragment
import com.chernyshev.bankingapp.base.viewBinding
import com.chernyshev.bankingapp.databinding.FragmentPinBinding
import com.chernyshev.bankingapp.utils.extensions.hideKeyboard
import com.chernyshev.bankingapp.utils.extensions.showAnimation
import com.chernyshev.bankingapp.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinFragment : BaseFragment(R.layout.fragment_pin) {

    private val viewModel by viewModels<PinViewModel>()
    private val binding by viewBinding<FragmentPinBinding>()
    private val args by navArgs<PinFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendEvent(Event.ReceivedArgs(args))
    }

    override fun initViews(): Unit = with(binding) {
        pinCodeView.onPinEntered {
            viewModel.sendEvent(Event.EnteredPin(it))
        }
        toolbar.onBackClicked {
            requireActivity().onBackPressed()
        }
    }

    override fun initObservers() {
        viewModel.subscribeToCommand(viewLifecycleOwner) { command ->
            when (command) {
                is Command.UpdateViews -> updateViews(command.currentStep)
                Command.NavigateToLanding -> navigateToLanding()
                Command.NavigateToTransactions -> navigateToTransactions()
                is Command.ShowError -> showError(command.errorType)
            }
        }
    }

    private fun navigateToLanding() {
        hideKeyboard()
        navController.navigate(PinFragmentDirections.navigateToLanding())
    }

    private fun navigateToTransactions() {
        hideKeyboard()
        navController.navigate(PinFragmentDirections.navigateToTransactions())
    }

    private fun updateViews(step: PageStep) = with(binding) {
        pageTitle.text = getString(step.titleResId)
        pinCodeView.clear()
    }

    private fun showError(errorType: ErrorType) = with(binding) {
        pinCodeView.showAnimation(R.anim.shake)
        pinCodeView.clear()
        showToast(errorType.errorMessageId)
    }

    override fun onResume() = with(binding) {
        super.onResume()
        pinCodeView.requestKeyboard()
    }
}