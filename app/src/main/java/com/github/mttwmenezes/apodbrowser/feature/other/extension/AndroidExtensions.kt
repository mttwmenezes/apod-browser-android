package com.github.mttwmenezes.apodbrowser.feature.other.extension

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun TextView.setClickableSpan(
    text: String,
    clickableText: String,
    onSpanClickListener: () -> Unit
) {
    val spannable = SpannableString(text)
    val spanStartIndex = text.indexOf(clickableText)
    val spanEndIndex = spanStartIndex + clickableText.length

    spannable.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            onSpanClickListener()
        }
    }, spanStartIndex, spanEndIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

    this.text = spannable
    movementMethod = LinkMovementMethod.getInstance()
}
