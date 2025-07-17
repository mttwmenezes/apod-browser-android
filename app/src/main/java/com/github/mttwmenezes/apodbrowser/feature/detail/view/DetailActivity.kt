package com.github.mttwmenezes.apodbrowser.feature.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.core.view.updatePadding
import coil3.load
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.ActivityDetailBinding
import com.github.mttwmenezes.apodbrowser.feature.other.extension.hide
import com.github.mttwmenezes.apodbrowser.feature.other.extension.openWebPage
import com.github.mttwmenezes.apodbrowser.feature.other.extension.shareUrl
import com.github.mttwmenezes.apodbrowser.feature.other.ui.SystemUI
import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @Inject lateinit var dateFormatter: DateFormatter
    @Inject lateinit var systemUI: SystemUI

    private lateinit var apod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = darkSystemBarStyle)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        apod = intent.extras?.getSerializable(ARG_APOD, Apod::class.java) as Apod
        setContentView(binding.root)
        configureContent()
        adjustContentPadding()
    }

    private val darkSystemBarStyle
        get() = SystemBarStyle.dark(
            ResourcesCompat.getColor(resources, android.R.color.transparent, null)
        )

    private fun configureContent() = with(binding.detailContent) {
        image.load(apodImageUrl)
        titleLabel.text = apod.title
        dateLabel.text = dateFormatter.format(Date.parse(apod.date), DateFormatter.Style.Full)
        explanationLabel.text = apod.explanation
        configureBottomBar()
    }

    private val apodImageUrl
        get() = if (apod.isImage) apod.url else apod.thumbnailUrl

    private fun configureBottomBar() = with(binding.bottomBar) {
        setNavigationOnClickListener { finish() }
        configureVisibleBottomBarActions()
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.play_video_action -> {
                    openWebPage(apod.url)
                    true
                }

                R.id.open_image_action -> {
                    // TODO To be implemented
                    true
                }

                R.id.open_in_browser_action -> {
                    openWebPage(getString(R.string.apod_url_format, apod.condensedDate))
                    true
                }

                R.id.image_copyright_action -> {
                    // TODO To be implemented
                    true
                }

                R.id.share_action -> {
                    shareUrl(getString(R.string.apod_url_format, apod.condensedDate))
                    true
                }

                else -> false
            }
        }
    }

    private fun configureVisibleBottomBarActions() = with(binding.bottomBar.menu) {
        if (apod.copyright.isNullOrBlank()) findItem(R.id.image_copyright_action).hide()
        when {
            apod.isImage -> findItem(R.id.play_video_action).hide()
            apod.isVideo -> findItem(R.id.open_image_action).hide()
            else -> configureActionsForOtherApod()
        }
    }

    private fun configureActionsForOtherApod() = with(binding.bottomBar.menu) {
        findItem(R.id.play_video_action).hide()
        findItem(R.id.open_image_action).hide()
    }

    private fun adjustContentPadding() = with(binding) {
        bottomBar.post {
            detailContent.root.updatePadding(
                bottom = systemUI.navigationBarHeight + bottomBar.height
            )
        }
    }

    companion object {
        private const val ARG_APOD = "apod"

        fun start(context: Context, apod: Apod) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARG_APOD, apod)
            context.startActivity(intent)
        }
    }
}
