package com.github.mttwmenezes.apodbrowser.feature.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var apod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        apod = intent.extras?.getSerializable(ARG_APOD, Apod::class.java) as Apod
        setContentView(binding.root)
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
