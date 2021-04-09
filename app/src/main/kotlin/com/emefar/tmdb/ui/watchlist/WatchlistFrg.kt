package com.emefar.tmdb.ui.watchlist


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
import com.emefar.tmdb.databinding.FragWatchlistBinding
import com.emefar.tmdb.model.MovieItem
import com.emefar.tmdb.utils.LDR
import com.emefar.tmdb.utils.SwipeToDeleteCallback
import com.emefar.tmdb.utils.extensions.showDismissDialog


class WatchlistFrg : BaseFragment() {


    private lateinit var binding: FragWatchlistBinding
    private lateinit var viewModel: WatchlistViewModel
    private val listAdapter: WatchlistListAdapter = WatchlistListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = WatchlistViewModelFactory()

        viewModel = ViewModelProviders.of(this, factory).get(WatchlistViewModel::class.java)
        viewModel.searchLiveData.observe(viewLifecycleOwner, this.dataObserver)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.frag_watchlist, container, false
        )
        val view = binding.root

        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.hasFixedSize()


        subscribe = listAdapter.clickEvent.subscribe { model ->

            findNavController().navigate(
                WatchlistFrgDirections.actionWatchlistDestToDetailsDest(
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

                viewModel.removeFromWatchlist(currentItem)
                val adapter = binding.rv.adapter as WatchlistListAdapter
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
        viewModel.getWatchlists()

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


