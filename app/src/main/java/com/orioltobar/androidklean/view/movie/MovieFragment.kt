package com.orioltobar.androidklean.view.movie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseFragment
import com.orioltobar.androidklean.extensions.getDominantColor
import com.orioltobar.androidklean.extensions.gone
import com.orioltobar.androidklean.extensions.visible
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.features.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.abs

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : BaseFragment() {

    private val args: MovieFragmentArgs by navArgs()

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.movie_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.execute(args.id)
        viewModel.movieDataStream.observe(
            viewLifecycleOwner,
            Observer { handleUiStates(it, ::processNewValue) })
    }

    private fun processNewValue(model: MovieModel) {
        movieFragmentTitle.text = model.title

        movieFragmentReleaseDateField.text = model.releaseDate
        movieFragmentOverview.text = model.overview

        context?.let {
            movieFragmentRateField.text = model.voteAverage.toString()
            // Image
            Glide.with(it)
                .load(model.frontImageUrl)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let {
                            movieFragmentBackground.setBackgroundColor(resource.getDominantColor())
                            movieFragmentBackground.background.alpha = 80
                        }
                        return false
                    }
                })
                .into(movieFragmentImage)
        }

        if (checkIfSwipeAnimationIsNeeded()) {
            initAppBarListener()
        } else {
            movieFragmentSwipeAnimation.gone()
        }
        progressBar.gone()
    }

    override fun onError(error: ErrorModel) {
        println("TRACK STATUS: ERROR!")
    }

    override fun onLoading() {
        progressBar.visible()
        println("TRACK STATUS: LOADING...")
    }

    private fun initAppBarListener() {
        movieFragmentAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (abs(verticalOffset) > 0) {
                movieFragmentSwipeAnimation.gone()
            } else {
                movieFragmentSwipeAnimation.visible()
            }
        })
    }

    private fun checkIfSwipeAnimationIsNeeded(): Boolean {
        val viewRawHeight = plant_detail_scrollview.measuredHeight
        val contentHeight = plant_detail_scrollview.getChildAt(0)?.height ?: 0

        return viewRawHeight - contentHeight < 0
    }

    companion object {
        const val TAG = "MovieFragment"
    }
}