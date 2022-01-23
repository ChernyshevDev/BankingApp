package com.chernyshev.bankingapp.data

import com.chernyshev.bankingapp.base.OperationResult
import com.chernyshev.bankingapp.domain.entity.Transaction
import com.chernyshev.bankingapp.domain.usecase.TransactionsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://sheet.best/"

class TransactionsUseCaseImpl : TransactionsUseCase {

    private val retrofit by lazy {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TransactionsApi::class.java)
    }

    override suspend fun getTransactions(): OperationResult<List<Transaction>> {
        val response = retrofit.getTransactions()
        return if (response.isSuccessful) {
            OperationResult.ResultSuccess(response.body() ?: emptyList())
        } else {
            OperationResult.ResultError(response.errorBody()?.toString())
        }
    }
}

interface TransactionsApi {
    @GET("/api/sheets/ebb5bfdc-efda-4966-9ecf-d2c171d6985a")
    suspend fun getTransactions(): Response<List<Transaction>>
}