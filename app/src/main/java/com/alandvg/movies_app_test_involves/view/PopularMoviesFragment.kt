package com.alandvg.movies_app_test_involves.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.alandvg.movies_app_test_involves.R
import com.alandvg.movies_app_test_involves.adapters.MoviesAdapter
import com.alandvg.movies_app_test_involves.databinding.MainFragmentBinding
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.viewmodel.PopularMoviesViewModel

class PopularMoviesFragment : Fragment() {

    private lateinit var viewModel: PopularMoviesViewModel
    private lateinit var adapter: MoviesAdapter
    private lateinit var binding: MainFragmentBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PopularMoviesViewModel::class.java)
        binding.rvMovies.layoutManager = LinearLayoutManager(activity!!)
        viewModel.itensPagedList.observe(viewLifecycleOwner, observerPagedList)

    }

}
