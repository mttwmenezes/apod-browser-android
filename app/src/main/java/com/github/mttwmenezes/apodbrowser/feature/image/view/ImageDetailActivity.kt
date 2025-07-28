package com.github.mttwmenezes.apodbrowser.feature.image.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.ActivityImageDetailBinding

class ImageDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailBinding

    private lateinit var apod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        apod = intent.extras?.getSerializable(ARG_APOD, Apod::class.java) as Apod
        setContentView(binding.root)
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
