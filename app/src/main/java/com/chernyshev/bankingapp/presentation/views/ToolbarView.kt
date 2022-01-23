package com.chernyshev.bankingapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.chernyshev.bankingapp.R
import com.chernyshev.bankingapp.databinding.ViewToolbarBinding
import com.chernyshev.bankingapp.utils.extensions.showNotImplementedToast

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding by lazy { ViewToolbarBinding.bind(this) }

    init {
        inflate(context, R.layout.view_toolbar, this)
        elevation = resources.getDimension(R.dimen.default_elevation)
        binding.settings.setOnClickListener { context.showNotImplementedToast() }
        context.obtainStyledAttributes(attrs, R.styleable.ToolbarView).run {
            val isBackVisible = getBoolean(R.styleable.ToolbarView_backVisible, true)
            binding.back.isVisible = isBackVisible
            recycle()
        }
    }

    fun onBackClicked(onClicked: () -> Unit) = with(binding) {
        back.setOnClickListener {
            onClicked()
        }
    }
}