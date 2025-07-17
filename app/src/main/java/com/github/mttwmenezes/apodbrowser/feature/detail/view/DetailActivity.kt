package com.github.mttwmenezes.apodbrowser.feature.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import coil3.load
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.ActivityDetailBinding
import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @Inject lateinit var dateFormatter: DateFormatter

    private lateinit var apod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = darkSystemBarStyle)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        apod = intent.extras?.getSerializable(ARG_APOD, Apod::class.java) as Apod
        setContentView(binding.root)
        configureContent()
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
    }

    private val apodImageUrl
        get() = if (apod.isImage) apod.url else apod.thumbnailUrl

    companion object {
        private const val ARG_APOD = "apod"

        fun start(context: Context, apod: Apod) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARG_APOD, apod)
            context.startActivity(intent)
        }
    }
}
