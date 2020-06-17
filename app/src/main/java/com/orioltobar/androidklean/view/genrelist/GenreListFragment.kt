package com.orioltobar.androidklean.view.genrelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseFragment
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.features.viewmodel.MovieGenresViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.genre_list_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class GenreListFragment : BaseFragment() {

    @Inject
    lateinit var genreListAdapter: GenreListAdapter

    private val viewModel: MovieGenresViewModel by viewModels()

    private var onGenreClicked: (MovieGenreDetailModel) -> Unit = { genre ->
        val direction =
            GenreListFragmentDirections.actionGenreListFragmentToMenuMovieListButton(genre.id)
        findNavController().navigate(direction)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.genre_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genreListAdapter.setOnClickListener(onGenreClicked)
        genreListRecyclerView.adapter = genreListAdapter

        viewModel.genreLiveData.observe(
            viewLifecycleOwner,
            Observer {
                handleUiStates(it, genreListAdapter::updateItems)
                genreListProgressBar.visibility = View.GONE
            })
    }

    override fun onError(error: ErrorModel) {
        println("ERROR GENRES")
    }

    override fun onLoading() {
        genreListProgressBar.visibility = View.VISIBLE
        println("LOADING GENRES")
    }
}
