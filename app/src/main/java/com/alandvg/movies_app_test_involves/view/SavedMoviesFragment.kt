package com.alandvg.movies_app_test_involves.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alandvg.movies_app_test_involves.R
import com.alandvg.movies_app_test_involves.adapters.MoviesAdapter
import com.alandvg.movies_app_test_involves.databinding.ListMoviesFragmentBinding
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.util.State
import com.alandvg.movies_app_test_involves.viewmodel.SavedMoviesViewModel


class SavedMoviesFragment : Fragment() {

    private lateinit var viewModel: SavedMoviesViewModel
    private lateinit var binding: ListMoviesFragmentBinding
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_movies_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get()

        adapter = MoviesAdapter ({ movie: Movie -> openMovie(movie) }, {movie : Movie -> saveMovie(movie) })
        binding.rvMovies.layoutManager = LinearLayoutManager(activity!!)


        adapter.setState(State.SUCCESS)

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

        })
        binding.rvMovies.adapter = adapter

        binding.refreshLayout.isEnabled = false
    }


    private fun openMovie(movie: Movie) {
        movie.id?.also {
            findNavController().navigate(SavedMoviesFragmentDirections.actionSavedMoviesFragmentToMovieDetailsFragment(it.toLong()))
        }
    }

    private fun saveMovie(movie: Movie) {
        viewModel.removeMovie(movie)
        Toast.makeText(activity, getString(R.string.lbl_movie_deleted), Toast.LENGTH_LONG).show()
    }


}
