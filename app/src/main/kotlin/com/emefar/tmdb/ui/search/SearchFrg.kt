package com.emefar.tmdb.ui.search


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emefar.tmdb.R
import com.emefar.tmdb.base.BaseFragment
import com.emefar.tmdb.base.BaseResponseMovie
import com.emefar.tmdb.databinding.FragSearchBinding
import com.emefar.tmdb.model.MovieItem
import com.emefar.tmdb.utils.LDR
import com.emefar.tmdb.utils.extensions.showDismissDialog


class SearchFrg : BaseFragment() {

    private lateinit var binding: FragSearchBinding
    private lateinit var viewModel: SearchViewModel
    private val listAdapter: SearchListAdapter = SearchListAdapter()
    private var currentPage = 1
    private var isLoading = false
    private var mainQuery = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = SearchViewModelFactory()

        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        viewModel.searchLiveData.observe(viewLifecycleOwner, this.dataObserver)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.frag_search, container, false
        )
        val view = binding.root

        val layoutManager = LinearLayoutManager(activity)
        binding.rv.layoutManager = layoutManager
        binding.rv.hasFixedSize()

        subscribe = listAdapter.clickEvent.subscribe { model ->

            findNavController().navigate(
                SearchFrgDirections.actionSearchDestToDetailsDest(
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

        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastPosition: Int = layoutManager.findLastVisibleItemPosition()
                val listSize: Int = listAdapter.itemCount
                if (!isLoading && listSize == (lastPosition + 1)) {
                    currentPage += 1
                    viewModel.loadMovies(mainQuery, currentPage)
                    isLoading = true
                }
            }
        })

        manageSearchView()

        return view
    }


    private fun manageSearchView() {

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false;
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?) = true
            override fun onQueryTextSubmit(query: String?) = true.also {
                binding.searchView.clearFocus()
                if (query != null) {
                    mainQuery = query
                    currentPage = 1
                    viewModel.loadMovies(mainQuery, currentPage)
                }

            }
        })

    }


    private val dataObserver = Observer<LDR<BaseResponseMovie>> { result ->
        when (result?.status) {
            LDR.Status.ERROR -> {
                showDismissDialog(result.err!!.localizedMessage!!)
                result.response?.movies
            }

            LDR.Status.SUCCESS -> {

                if (result.response!!.movies.isNotEmpty()) {
                    if (result.response.page == 1) {
                        listAdapter.clear()
                    }
                    binding.searchTip.visibility = View.GONE
                    listAdapter.updateList(result.response.movies)
                    isLoading = false
                }

            }

            else -> {
            }
        }
    }

}


