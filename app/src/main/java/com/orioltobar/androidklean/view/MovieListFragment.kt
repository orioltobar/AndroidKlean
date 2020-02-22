package com.orioltobar.androidklean.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseFragment
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.movie_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationButton.setOnClickListener {
            val direction = MovieListFragmentDirections.actionMovieListFragmentToMovieFragment(550L)
            findNavController().navigate(direction)
        }
    }
}