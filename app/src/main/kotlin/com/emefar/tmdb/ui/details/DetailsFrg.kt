package com.emefar.tmdb.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.emefar.tmdb.R
import com.emefar.tmdb.base.BaseFragment
import com.emefar.tmdb.databinding.FragDetailsBinding
import com.emefar.tmdb.model.MovieItem
import com.emefar.tmdb.utils.extensions.toast
import com.fxn.stash.Stash


class DetailsFrg : BaseFragment() {

    private val args by navArgs<DetailsFrgArgs>()
    private lateinit var binding: FragDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.frag_details, container, false
        )
        binding.model = args.model


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.addToFavorites.setOnClickListener { clickFavorites() }
        binding.addToWatchlist.setOnClickListener { clickWatchlist() }

    }

    private fun clickFavorites() {

        val favoritesArrayList: ArrayList<MovieItem> =
            Stash.getArrayList<MovieItem>(
                "FAVORITES",
                MovieItem::class.java
            )

        for (movie in favoritesArrayList) {


            if (movie.id == args.model.id) {

                toast(getString(R.string.already_added_favorites))
                return
            }
        }

        val posterPath = args.model.posterPath.replace("http://image.tmdb.org/t/p/w500","")
        favoritesArrayList.add(
            MovieItem(
                args.model.id,
                args.model.voteAverage,
                args.model.title,
                posterPath,
                args.model.overview,
                args.model.releaseDate

            )
        )

        Stash.put("FAVORITES", favoritesArrayList)
        toast(getString(R.string.succeed))

    }

    private fun clickWatchlist() {

        val watchlistArrayList: ArrayList<MovieItem> =
            Stash.getArrayList<MovieItem>(
                "WATCHLIST",
                MovieItem::class.java
            )

        for (movie in watchlistArrayList) {


            if (movie.id == args.model.id) {

                toast(getString(R.string.already_added_favorites))
                return
            }
        }

        val posterPath = args.model.posterPath.replace("http://image.tmdb.org/t/p/w500","")

        watchlistArrayList.add(
            MovieItem(
                args.model.id,
                args.model.voteAverage,
                args.model.title,
                posterPath,
                args.model.overview,
                args.model.releaseDate

            )
        )

        Stash.put("WATCHLIST", watchlistArrayList)
        toast(getString(R.string.succeed))


    }


}
