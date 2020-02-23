package com.orioltobar.androidklean.view.genrelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseFragment
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.features.UiStatus
import com.orioltobar.features.viewmodel.MovieGenresViewModel
import kotlinx.android.synthetic.main.genre_list_fragment.*
import javax.inject.Inject

class GenreListFragment : BaseFragment() {

    @Inject
    lateinit var genreListAdapter: GenreListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.genre_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genreListAdapter.setOnClickListener { genre ->
            val direction =
                GenreListFragmentDirections.actionGenreListFragmentToMenuMovieListButton(genre.id)
            findNavController().navigate(direction)
        }
        genreListRecyclerView.adapter = genreListAdapter

        val viewModel = ViewModelProvider(this, vmFactory).get(MovieGenresViewModel::class.java)
        viewModel.genreLiveData.observe(
            viewLifecycleOwner,
            Observer<UiStatus<List<MovieGenreDetailModel>, ErrorModel>> {
                handleUiStates(it, genreListAdapter::updateItems)
                progressBar.visibility = View.GONE
            })
    }

    override fun onError(error: ErrorModel) {
        println("ERROR GENRES")
    }

    override fun onLoading() {
        progressBar.visibility = View.VISIBLE
        println("LOADING GENRES")
    }
}
