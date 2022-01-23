package com.chernyshev.bankingapp.presentation.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chernyshev.bankingapp.R
import com.chernyshev.bankingapp.databinding.ItemTransactionBinding
import com.chernyshev.bankingapp.domain.entity.Transaction
import com.chernyshev.bankingapp.domain.entity.TransactionType

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {
    private val items = mutableListOf<Transaction>()
    fun updateItems(newItems: List<Transaction>) {
        items.apply {
            clear()
            addAll(newItems)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Transaction) = with(binding) {
            val (backgroundResId, textColorId) = if (item.type == TransactionType.credit) {
                R.drawable.bg_transaction_credit to R.color.banking_green
            } else {
                R.drawable.bg_transaction_debit to R.color.banking_grey
            }
            root.setBackgroundResource(backgroundResId)

            type.apply {
                setTextColor(ContextCompat.getColor(context, textColorId))
                type.text = item.type.name
            }
            amount.text = binding.root.context.getString(R.string.amount_eur, item.amount)
            counterPartyAccount.text = item.counterPartyAccount
            counterPartyName.text = item.counterPartyName
            date.text = item.date
            transactionId.text = item.id
            description.text = item.description
        }
    }
}