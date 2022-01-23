package com.chernyshev.bankingapp.domain.entity

data class Transaction(
    val id: String,
    val counterPartyName: String,
    val counterPartyAccount: String,
    val type: TransactionType,
    val amount: String,
    val description: String,
    val date: String
)

enum class TransactionType {
    credit, debit
}