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

package com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.feature.other.ui.SystemUI
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BookmarksFeedSpacingDecoration @Inject constructor(
    @ApplicationContext private val context: Context,
    private val systemUI: SystemUI
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val position = parent.getChildAdapterPosition(view)
        val spanIndex = layoutParams.spanIndex

        if (spanIndex == 0) {
            outRect.left = marginExtraSmall
            outRect.right = marginExtraSmall / 2
        } else {
            outRect.left = marginExtraSmall / 2
            outRect.right = marginExtraSmall
        }

        outRect.top = if (position == 0 || position == 1) marginSmall else 0
        outRect.bottom = if (isLastItem(position, state)) {
            systemUI.navigationBarHeight + marginRegular
        } else {
            marginSmall
        }
    }

    private val marginExtraSmall
        get() = context.resources.getDimensionPixelSize(R.dimen.margin_extra_small)

    private val marginSmall
        get() = context.resources.getDimensionPixelSize(R.dimen.margin_small)

    private fun isLastItem(position: Int, state: State) = position == state.itemCount - 1

    private val marginRegular
        get() = context.resources.getDimensionPixelSize(R.dimen.margin_regular)
}
