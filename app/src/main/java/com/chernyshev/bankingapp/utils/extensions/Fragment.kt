package com.chernyshev.bankingapp.utils.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(@StringRes messageResId: Int) {
    Toast.makeText(context, requireContext().getString(messageResId), Toast.LENGTH_SHORT).show()
}

fun Fragment.hideKeyboard() {
    requireActivity().currentFocus?.let {
        val inputManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}