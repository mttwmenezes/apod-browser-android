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

package com.github.mttwmenezes.apodbrowser.feature.image.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import coil3.load
import coil3.request.crossfade
import coil3.size.Size
import coil3.toBitmap
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.ActivityImageDetailBinding
import com.github.mttwmenezes.apodbrowser.feature.other.extension.getColorFromAttr
import com.github.mttwmenezes.apodbrowser.feature.other.extension.hide
import com.github.mttwmenezes.apodbrowser.feature.other.extension.show
import com.github.mttwmenezes.apodbrowser.feature.other.image.DeviceGallery
import com.github.mttwmenezes.apodbrowser.feature.other.image.ImageSharer
import com.google.android.material.R.attr.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailBinding

    @Inject lateinit var messages: ImageDetailMessages
    @Inject lateinit var deviceGallery: DeviceGallery
    @Inject lateinit var imageSharer: ImageSharer

    private lateinit var apod: Apod

    private var isImmersiveMode = false
        set(value) {
            field = value
            onImmersiveModeChanged()
        }

    private fun onImmersiveModeChanged() {
        if (isImmersiveMode) hideBars() else showBars()
    }

    private fun hideBars() = with(binding) {
        appBarLayout.hide()
        titleBar.hide()
        hideSystemBars()
    }

    private fun hideSystemBars() {
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private val windowInsetsController
        get() = WindowCompat.getInsetsController(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    private fun showBars() = with(binding) {
        appBarLayout.show()
        titleBar.show()
        showSystemBars()
    }

    private fun showSystemBars() {
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        apod = intent.extras?.getSerializable(ARG_APOD, Apod::class.java) as Apod
        setContentView(binding.root)
        configureUi()
        loadImage()
        applyWindowInsets()
    }

    private fun configureUi() = with(binding) {
        topAppBar.setNavigationOnClickListener { finish() }
        root.setOnClickListener { isImmersiveMode = !isImmersiveMode }
        image.setOnClickListener { isImmersiveMode = !isImmersiveMode }
        titleLabel.text = apod.title
    }

    private fun loadImage() = with(binding) {
        image.load(if (apod.hasHdImage) apod.hdUrl else sdImageUrl) {
            size(Size.ORIGINAL)
            crossfade(true)
            listener(
                onStart = { onImageLoadStarted() },
                onCancel = { onImageLoadFailed() },
                onError = { _, _ -> onImageLoadFailed() },
                onSuccess = { _, _ -> onImageLoadSuccess() }
            )
        }
    }

    private val sdImageUrl
        get() = if (apod.isImage) apod.url else apod.thumbnailUrl

    private fun onImageLoadStarted() = with(binding) {
        topAppBar.menu.clear()
        progressIndicator.show()
    }

    private fun onImageLoadFailed() = with(binding) {
        topAppBar.menu.clear()
        progressIndicator.hide()
        setupImageForErrorState()
        messages.showImageUnavailableMessage(root, anchor = titleBar)
    }

    private fun setupImageForErrorState() = with(binding.image) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            dimensionRatio = "16:9"
            adjustViewBounds = false
            background = context.getColorFromAttr(colorSurfaceContainerHighest).toDrawable()
            setImageDrawable(brokenImageDrawable)
            updatePadding(
                left = paddingExtraLarge,
                top = paddingExtraLarge,
                right = paddingExtraLarge,
                bottom = paddingExtraLarge
            )
        }
    }

    private val brokenImageDrawable
        get() = ResourcesCompat.getDrawable(resources, R.drawable.ic_broken_image, null)?.apply {
            setTint(ResourcesCompat.getColor(resources, R.color.surface_50, null))
        }

    private val paddingExtraLarge
        get() = resources.getDimensionPixelSize(R.dimen.padding_extra_large)

    private fun onImageLoadSuccess() = with(binding) {
        setupTopAppBarMenu()
        progressIndicator.hide()
        setupImageForSuccessState()
    }

    private fun setupTopAppBarMenu() = with(binding.topAppBar) {
        inflateMenu(R.menu.image_detail_top_app_bar_menu)
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.download_action -> {
                    downloadImage()
                    true
                }

                R.id.share_action -> {
                    shareImage()
                    true
                }

                else -> false
            }
        }
    }

    private fun downloadImage() {
        binding.image.load(if (apod.hasHdImage) apod.hdUrl else sdImageUrl) {
            size(Size.ORIGINAL)
            listener(
                onCancel = { showUnexpectedErrorMessage() },
                onError = { _, _ -> showUnexpectedErrorMessage() },
                onSuccess = { _, result -> onImageDownloadSuccess(result.image.toBitmap()) }
            )
        }
    }

    private fun showUnexpectedErrorMessage() = with(binding) {
        messages.showUnexpectedErrorMessage(root, anchor = titleBar)
    }

    private fun onImageDownloadSuccess(image: Bitmap) = with(binding) {
        deviceGallery.add(image, apod.imageFilename)
        messages.showImageDownloadCompletedMessage(root = root, anchor = titleBar)
    }

    private fun shareImage() {
        binding.image.load(if (apod.hasHdImage) apod.hdUrl else sdImageUrl) {
            size(Size.ORIGINAL)
            listener(
                onCancel = { showUnexpectedErrorMessage() },
                onError = { _, _ -> showUnexpectedErrorMessage() },
                onSuccess = { _, result ->
                    imageSharer.share(
                        result.image.toBitmap(),
                        onFailure = { showUnexpectedErrorMessage() }
                    )
                }
            )
        }
    }

    private fun setupImageForSuccessState() = with(binding.image) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            dimensionRatio = null
            adjustViewBounds = true
            background = null
            updatePadding(left = 0, top = 0, right = 0, bottom = 0)
        }
    }

    private fun applyWindowInsets() = with(binding) {
        ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            topAppBar.updatePadding(left = systemBars.left, right = systemBars.right)
            titleLabel.updatePadding(bottom = systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val ARG_APOD = "apod"

        fun start(context: Context, apod: Apod) {
            val intent = Intent(context, ImageDetailActivity::class.java)
            intent.putExtra(ARG_APOD, apod)
            context.startActivity(intent)
        }
    }
}
