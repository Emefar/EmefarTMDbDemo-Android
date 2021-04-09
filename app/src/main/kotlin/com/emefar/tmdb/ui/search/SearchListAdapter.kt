package com.emefar.tmdb.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.emefar.tmdb.R
import com.emefar.tmdb.base.BaseRecyclerAdapter
import com.emefar.tmdb.base.BaseViewHolder
import com.emefar.tmdb.databinding.ItemMovieSimpleBinding
import com.emefar.tmdb.model.MovieItem
import timber.log.Timber

class SearchListAdapter :
    BaseRecyclerAdapter<BaseViewHolder<MovieItem>, MovieItem, MovieItem>() {
    override fun onBindViewHolder(holder: BaseViewHolder<MovieItem>, position: Int) {
        holder.bindItems(modelList[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemMovieSimpleBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie_simple,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class ViewHolder(private val binding: ItemMovieSimpleBinding) :
        BaseViewHolder<MovieItem>(binding) {

        init {
            binding.cv.setOnClickListener {
                Timber.i("position clicked $layoutPosition")
                clickSubject.onNext(
                    MovieItem(
                        id = modelList[layoutPosition].id,
                        title = modelList[layoutPosition].title,
                        voteAverage = modelList[layoutPosition].voteAverage,
                        posterPath = modelList[layoutPosition].getThumbURL(),
                        overview = modelList[layoutPosition].overview,
                        releaseDate = modelList[layoutPosition].releaseDate
                    )
                )
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bindItems(model: MovieItem) {
            binding.model = model
        }
    }
}