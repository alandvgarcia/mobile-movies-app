package com.alandvg.movies_app_test_involves.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.alandvg.movies_app_test_involves.R
import com.alandvg.movies_app_test_involves.adapters.MoviesAdapter
import com.alandvg.movies_app_test_involves.databinding.SearchMoviesFragmentBinding
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.paging.MoviesDataSource
import com.alandvg.movies_app_test_involves.util.KeyboardUtil
import com.alandvg.movies_app_test_involves.util.State
import com.alandvg.movies_app_test_involves.viewmodel.MoviesViewModel


class SearchMoviesFragment : Fragment() {


    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: SearchMoviesFragmentBinding
    private lateinit var adapter: MoviesAdapter


    private val observerPagedList = Observer<PagedList<Movie>> {
        adapter = MoviesAdapter { movie: Movie -> openMovie(movie) }
        binding.rvMoviesSearch.adapter = adapter
        viewModel.getState()?.observe(viewLifecycleOwner, observerState)
        adapter.submitList(it)
    }

    private val observerState = Observer<State> {
        if (it == State.LOADING && (viewModel.listIsEmpty())) {
            // binding.refreshLayout.isRefreshing = true
        } else {
            // binding.refreshLayout.isRefreshing = false
            adapter.setState(it ?: State.SUCCESS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.search_movies_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this




        binding.tieSearch.addTextChangedListener {

            if (savedInstanceState?.getString("search") != it.toString()) {
                viewModel.getMovies(MoviesDataSource.SEARCH, it.toString())
            }
            addObservables()
        }

        binding.tieSearch.requestFocus()
        KeyboardUtil.showKeyboard(activity!!, binding.tieSearch)

        binding.rvMoviesSearch.layoutManager = LinearLayoutManager(activity!!)


    }

    private fun addObservables() {
        if (viewModel.itensPagedList?.hasActiveObservers() == true)
            viewModel.itensPagedList?.removeObserver(observerPagedList)

        if (viewModel.getState()?.hasActiveObservers() == true)
            viewModel.getState()?.removeObserver(observerState)

        viewModel.itensPagedList?.observe(viewLifecycleOwner, observerPagedList)
        viewModel.getState()?.observe(viewLifecycleOwner, observerState)
    }

    private fun openMovie(movie: Movie) {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("search", binding.tieSearch.text.toString())

    }

}
