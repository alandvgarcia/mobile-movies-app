package com.alandvg.movies_app_test_involves.viewholders

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alandvg.movies_app_test_involves.databinding.ItemMovieRvBinding
import com.alandvg.movies_app_test_involves.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieViewHolder(private val binding: ItemMovieRvBinding, private val context : Context) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.tvDescricao.text = movie.overview
        binding.tvTitulo.text = movie.title
        binding.tvDateRelease.text = movie.release_date
        binding.imgPoster.background = ContextCompat.getDrawable(context, android.R.color.darker_gray)

        Glide.with(context)
            .load("http://image.tmdb.org/t/p/w185/" + movie.poster_path)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgPoster)
    }

}
