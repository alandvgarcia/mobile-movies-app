package com.alandvg.movies_app_test_involves.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alandvg.movies_app_test_involves.R
import com.alandvg.movies_app_test_involves.databinding.MovieDetailsFragmentBinding
import com.alandvg.movies_app_test_involves.util.DateUtil
import com.alandvg.movies_app_test_involves.util.State
import com.alandvg.movies_app_test_involves.viewmodel.MovieDetailsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.chip.Chip


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: MovieDetailsFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setTitle("")

        binding =
            DataBindingUtil.setContentView(this, R.layout.movie_details_fragment)

        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.also {
            viewModel.loadMovie(MovieDetailsActivityArgs.fromBundle(it).movieId)
        } ?: run {
            finish()
        }

        viewModel.getMovie().observe(this, Observer { movie ->

            supportActionBar?.setTitle(movie.title)
            binding.tvDescription.text = movie.overview
            binding.tvTitulo.text = movie.title
            binding.tvDateRelease.text = movie.releaseDate?.let {
                DateUtil.dateTimeToDateFormatLocalDefault(
                    it
                )
            } ?: movie.releaseDate

            binding.imgPoster.background =
                ContextCompat.getDrawable(binding.root.context, android.R.color.darker_gray)


            binding.tvGenre.setChipSpacing(16)
            binding.tvGenre.isSelected = false

            movie.genres?.forEach {
                val chip = Chip(this)
                chip.text = it.name
                binding.tvGenre.addView(chip)
            }

            Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500/" + movie.backdropPath)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgPoster)
        })


        viewModel.getState().observe(this, Observer {
            when (it) {
                State.FAIL -> finish()
            }
        })

    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}
