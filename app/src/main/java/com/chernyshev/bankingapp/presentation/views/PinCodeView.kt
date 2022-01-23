package com.chernyshev.bankingapp.presentation.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.chernyshev.bankingapp.R
import com.chernyshev.bankingapp.databinding.ViewPinFieldBinding

class PinCodeFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding by lazy { ViewPinFieldBinding.bind(this) }

    init {
        inflate(context, R.layout.view_pin_field, this)
        setupEditTexts()
    }

    private fun setupEditTexts() = with(binding) {
        invisibleEditText.apply {
            doOnTextChanged { text, _, _, _ ->
                val firstChar = text?.getOrNull(0) ?: ""
                val secondChar = text?.getOrNull(1) ?: ""
                val thirdChar = text?.getOrNull(2) ?: ""
                val forthChar = text?.getOrNull(3) ?: ""
                first.root.text = firstChar.toString()
                second.root.text = secondChar.toString()
                third.root.text = thirdChar.toString()
                forth.root.text = forthChar.toString()
            }
            requestFocus()
        }
        requestKeyboard()
    }

    fun clear() = with(binding) {
        invisibleEditText.setText("")
    }

    fun onPinEntered(onEntered: (String) -> Unit) = with(binding) {
        invisibleEditText.doOnTextChanged { text, _, _, _ ->
            if (text?.length == 4) {
                onEntered(text.toString())
            }
        }
    }

    fun requestKeyboard() {
        (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            showSoftInput(binding.invisibleEditText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}