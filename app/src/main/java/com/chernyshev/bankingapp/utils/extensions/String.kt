package com.chernyshev.bankingapp.utils.extensions

fun String.tryToDouble(): Double {
    return runCatching {
        this.toDouble()
    }.getOrElse {
        0.0
    }
}