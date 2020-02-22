package com.orioltobar.androidklean.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseFragment
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.features.MovieViewModel
import kotlinx.android.synthetic.main.movie_fragment.*

class MovieFragment : BaseFragment() {

    private val args: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.movie_fragment, container,  false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, vmFactory).get(MovieViewModel::class.java)
        viewModel.execute(args.id)

        viewModel.movieUiModel.observe(this, Observer<MovieModel>(::displayTestData))
    }

    private fun displayTestData(movie: MovieModel) {
        textView.text = movie.originalTitle
    }
}