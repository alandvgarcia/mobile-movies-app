package com.alandvg.movies_app_test_involves.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.alandvg.movies_app_test_involves.R
import com.alandvg.movies_app_test_involves.adapters.MoviesAdapter
import com.alandvg.movies_app_test_involves.databinding.ListMoviesFragmentBinding
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.paging.MoviesDataSource
import com.alandvg.movies_app_test_involves.util.State
import com.alandvg.movies_app_test_involves.viewmodel.MoviesViewModel

class ListTopRatedMoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MoviesAdapter
    private lateinit var binding: ListMoviesFragmentBinding

    private val observerPagedList = Observer<PagedList<Movie>> {
        adapter = MoviesAdapter ({ movie: Movie -> openMovie(movie) }, {movie : Movie -> saveMovie(movie) })
        binding.rvMovies.adapter = adapter
        viewModel.getState()?.observe(viewLifecycleOwner, observerState)
        adapter.submitList(it)
    }


    private val observerState = Observer<State> {
        if (it == State.LOADING && (viewModel.listIsEmpty())) {
            binding.refreshLayout.isRefreshing = true
        } else {
            binding.refreshLayout.isRefreshing = false
            adapter.setState(it ?: State.SUCCESS)
        }
    }


    private fun openMovie(movie: Movie) {
        movie.id?.also {
            findNavController().navigate(ListTopRatedMoviesFragmentDirections.actionListTopRatedMoviesFragmentToMovieDetailsFragment(it.toLong()))
        }
    }

    private fun saveMovie(movie: Movie) {
        viewModel.saveMovie(movie)
        Toast.makeText(activity, getString(R.string.lbl_movie_saved), Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_movies_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (viewModel.listIsEmpty()) {
            viewModel.getMovies(MoviesDataSource.TOP_RATED)
        }

        binding.rvMovies.layoutManager = LinearLayoutManager(activity!!)
        viewModel.itensPagedList?.observe(viewLifecycleOwner, observerPagedList)


        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

}
