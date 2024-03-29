package com.alandvg.movies_app_test_involves.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alandvg.movies_app_test_involves.R
import com.alandvg.movies_app_test_involves.databinding.ItemMovieRvBinding
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.util.State
import com.alandvg.movies_app_test_involves.viewholders.ListFooterViewHolder
import com.alandvg.movies_app_test_involves.viewholders.MovieViewHolder
import kotlin.math.hypot
import kotlin.math.roundToInt

class MoviesAdapter(private val openMovie: (Movie) -> Unit, private val saveMovie : (Movie) -> Unit) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val ITEM = 1
    private val FOOTER_LOADING = 2
    private var state = State.LOADING
    private lateinit var binding: ItemMovieRvBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_movie_rv,
                    parent,
                    false
                )
                return MovieViewHolder(
                    binding
                )
            }
            else -> ListFooterViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM) {
            holder as MovieViewHolder
            getItem(position)?.let { movie ->
                holder.bind(movie)


                holder.itemView.setOnLongClickListener {
                    saveMovie(movie)
                    true
                }

                holder.itemView.setOnClickListener {
                    openMovie(movie)
                }
            }
        } else (holder as ListFooterViewHolder).bind(state)
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.FAIL)
    }


    override fun getItemCount(): Int {
        return (super.getItemCount()) + if (hasFooter()) 1 else 0
    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> =
            object : DiffUtil.ItemCallback<Movie>() {

                override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                    return oldItem == newItem
                }
            }
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount())
            ITEM
        else
            FOOTER_LOADING
    }


}