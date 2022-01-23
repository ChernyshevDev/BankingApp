package com.chernyshev.bankingapp.presentation.transactions

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.chernyshev.bankingapp.R
import com.chernyshev.bankingapp.base.BaseFragment
import com.chernyshev.bankingapp.base.viewBinding
import com.chernyshev.bankingapp.databinding.FragmentTransactionsBinding
import com.chernyshev.bankingapp.domain.entity.Transaction
import com.chernyshev.bankingapp.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : BaseFragment(R.layout.fragment_transactions) {

    private val viewModel by viewModels<TransactionsViewModel>()
    private val binding by viewBinding<FragmentTransactionsBinding>()
    private val transactionsAdapter = TransactionsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendEvent(Event.PageOpened)
    }

    override fun initViews() = with(binding) {
        toolbar.onBackClicked { requireActivity().onBackPressed() }
        transactionsRecycler.adapter = transactionsAdapter
    }

    override fun initObservers() {
        viewModel.subscribeToCommand(viewLifecycleOwner) { command ->
            when (command) {
                is Command.SetTransactions -> setTransactions(
                    command.transactions,
                    command.transactionsSum
                )
                is Command.DisplayError -> showToast(
                    command.errorMessage ?: getString(R.string.unknown_error)
                )
            }
        }

        viewModel.subscribeToStateUpdates(viewLifecycleOwner) { state ->
            displayLoading(state.isLoading)
        }
    }

    private fun displayLoading(isLoading: Boolean) = with(binding) {
        shimmer.apply {
            transactionsFakeList.root.isVisible = isLoading
            if (isLoading) {
                showShimmer(true)
            } else {
                hideShimmer()
            }
        }
    }

    private fun setTransactions(transactions: List<Transaction>, transactionsSum: String) =
        with(binding) {
            this.transactionsSum.text = getString(R.string.transactions_sum, transactionsSum)
            transactionsAdapter.updateItems(transactions)
        }
}