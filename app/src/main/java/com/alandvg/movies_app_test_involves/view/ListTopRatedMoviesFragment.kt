package com.alandvg.movies_app_test_involves.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.ui.NavigationUI
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.alandvg.movies_app_test_involves.R
import com.alandvg.movies_app_test_involves.adapters.MoviesAdapter
import com.alandvg.movies_app_test_involves.databinding.ListMoviesFragmentBinding
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.paging.MoviesDataSource
import com.alandvg.movies_app_test_involves.viewmodel.MoviesViewModel

class ListTopRatedMoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MoviesAdapter
    private lateinit var binding: ListMoviesFragmentBinding

    private val observerPagedList = Observer<PagedList<Movie>> {
        adapter = MoviesAdapter { movie: Movie -> openMovie(movie) }
        binding.rvMovies.adapter = adapter
        adapter.submitList(it)
    }

    private fun openMovie(movie: Movie) {

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

        if (viewModel.listIsEmpty())
            viewModel.getMovies(MoviesDataSource.TOP_RATED)

        binding.rvMovies.layoutManager = LinearLayoutManager(activity!!)
        viewModel.itensPagedList?.observe(viewLifecycleOwner, observerPagedList)
    }

}
