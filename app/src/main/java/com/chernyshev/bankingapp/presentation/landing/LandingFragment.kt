package com.chernyshev.bankingapp.presentation.landing

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.chernyshev.bankingapp.NavGraphDirections
import com.chernyshev.bankingapp.base.BaseFragment
import com.chernyshev.bankingapp.R
import com.chernyshev.bankingapp.base.viewBinding
import com.chernyshev.bankingapp.databinding.FragmentLandingBinding
import com.chernyshev.bankingapp.domain.usecase.Balances
import com.chernyshev.bankingapp.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : BaseFragment(R.layout.fragment_landing) {

    private val viewModel by viewModels<LandingViewModel>()
    private val binding by viewBinding<FragmentLandingBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendEvent(Event.OpenedPage)
    }

    override fun initObservers() {
        viewModel.subscribeToCommand(viewLifecycleOwner) { command ->
            when (command) {
                Command.NavigateToPin -> navController.navigate(
                    NavGraphDirections.actionGlobalPinFragment(
                        false
                    )
                )
                is Command.SetBalance -> setBalance(command.balances)
                is Command.ShowError -> showToast(
                    command.errorMessage ?: getString(R.string.unknown_error)
                )
            }
        }

        viewModel.subscribeToStateUpdates(viewLifecycleOwner) {
            displayBalanceLoading(it.isLoadingBalance)
        }
    }

    override fun initClickListeners() = with(binding) {
        transactions.setOnClickListener { viewModel.sendEvent(Event.ClickedTransactions) }
    }

    private fun setBalance(balances: Balances) = with(binding) {
        creditBalance.text = getString(R.string.amount_eur, balances.creditBalance)
        debitBalance.text = getString(R.string.amount_eur, balances.debitBalance)
    }

    private fun displayBalanceLoading(isLoading: Boolean) = with(binding) {
        balanceLoading.root.isVisible = isLoading
    }
}