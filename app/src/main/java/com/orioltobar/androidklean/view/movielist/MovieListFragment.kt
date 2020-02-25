package com.orioltobar.androidklean.view.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseFragment
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.features.UiStatus
import com.orioltobar.features.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.movie_list_fragment.*
import javax.inject.Inject

class MovieListFragment : BaseFragment() {

    @Inject
    lateinit var movieListAdapter: MovieListAdapter

    private val navArgs: MovieListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.movie_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListAdapter.setOnClickListener { movie ->
            val direction =
                MovieListFragmentDirections.actionMovieListFragmentToMovieFragment(movie.id)
            findNavController().navigate(direction)
        }
        movieListRecyclerView.adapter = movieListAdapter

        val viewModel = ViewModelProvider(this, vmFactory).get(MovieListViewModel::class.java)
        viewModel.getMovieListFromGenre(navArgs.id)
        viewModel.movieListDataStream.observe(
            viewLifecycleOwner,
            Observer<UiStatus<List<MovieModel>, ErrorModel>> {
                handleUiStates(
                    it,
                    ::processNewValue
                )
            })
    }

    private fun processNewValue(values: List<MovieModel>) {
        movieListAdapter.updateItems(values)
        progressBar.visibility = View.GONE
    }

    override fun onError(error: ErrorModel) {
        println("TRACK STATUS: ERROR!")
    }

    override fun onLoading() {
        progressBar.visibility = View.VISIBLE
        println("TRACK STATUS: LOADING...")
    }
}