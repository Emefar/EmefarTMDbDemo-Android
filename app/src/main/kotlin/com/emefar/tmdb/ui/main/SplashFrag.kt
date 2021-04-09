package com.emefar.tmdb.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emefar.tmdb.R
import com.fxn.stash.Stash


class SplashFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Stash.init(requireContext())

        val isUserLogin = Stash.getBoolean("IS_USER_LOGGED_IN", false)

        if (isUserLogin) {
            findNavController().navigate(R.id.action_splashFrag_to_mainActivity)
        } else {
            findNavController().navigate(R.id.action_splashFrag_to_loginActivity)
        }

        activity?.finish()

    }
}
