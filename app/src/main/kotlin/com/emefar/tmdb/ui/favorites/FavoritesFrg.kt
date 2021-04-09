package com.emefar.tmdb.ui.favorites


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emefar.tmdb.R
import com.emefar.tmdb.base.BaseFragment
import com.emefar.tmdb.base.BaseResponseMovie
import com.emefar.tmdb.databinding.FragFavoritesBinding
import com.emefar.tmdb.model.MovieItem
import com.emefar.tmdb.utils.LDR
import com.emefar.tmdb.utils.SwipeToDeleteCallback
import com.emefar.tmdb.utils.extensions.showDismissDialog


class FavoritesFrg : BaseFragment() {


    private lateinit var binding: FragFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel
    private val listAdapter: FavoritesListAdapter = FavoritesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = FavoritesViewModelFactory()

        viewModel = ViewModelProviders.of(this, factory).get(FavoritesViewModel::class.java)
        viewModel.searchLiveData.observe(viewLifecycleOwner, this.dataObserver)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.frag_favorites, container, false
        )
        val view = binding.root

        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.hasFixedSize()




        subscribe = listAdapter.clickEvent.subscribe { model ->

            findNavController().navigate(
                FavoritesFrgDirections.actionFavoritesDestToDetailsDest(
                    MovieItem(
                        model.id,
                        model.voteAverage,
                        model.title,
                        model.posterPath,
                        model.overview,
                        model.releaseDate
                    )
                )
            )


        }
        binding.viewModel = viewModel
        binding.postListAdapter = listAdapter


        val swipeHandler = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentItem = viewHolder.adapterPosition

                viewModel.removeFromFavorites(currentItem)
                val adapter = binding.rv.adapter as FavoritesListAdapter
                adapter.removeAt(currentItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rv)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // viewModel.loadMovies("Black", currentPage)
        viewModel.getFavorites()

    }


    private val dataObserver = Observer<LDR<BaseResponseMovie>> { result ->
        when (result?.status) {
            LDR.Status.ERROR -> {
                showDismissDialog(result.err!!.localizedMessage!!)
                result.response?.movies
            }

            LDR.Status.SUCCESS -> {
                listAdapter.clear()
                listAdapter.updateList(result.response!!.movies)
            }

            else -> {
            }
        }
    }

}


