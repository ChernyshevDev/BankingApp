package com.chernyshev.bankingapp.utils.extensions

import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun View.showAnimation(@AnimRes animationResId: Int) =
    this.startAnimation(AnimationUtils.loadAnimation(context, animationResId))