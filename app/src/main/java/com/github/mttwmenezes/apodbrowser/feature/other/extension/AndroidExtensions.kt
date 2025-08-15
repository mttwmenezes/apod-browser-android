/*
 * Copyright 2025 Matheus Menezes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mttwmenezes.apodbrowser.feature.other.extension

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.net.toUri

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun MenuItem.hide() {
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

inline fun SearchView.setOnQueryTextChangedListener(crossinline onTextChanged: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = false

        override fun onQueryTextChange(newText: String?): Boolean {
            onTextChanged(newText.orEmpty())
            return true
        }
    })
}

fun Context.openWebPage(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
}

fun Context.shareUrl(url: String, chooserTitle: String? = null) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }

    startActivity(Intent.createChooser(intent, chooserTitle))
}

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}
