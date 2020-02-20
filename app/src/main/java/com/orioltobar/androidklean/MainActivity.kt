package com.orioltobar.androidklean

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.orioltobar.androidklean.base.BaseActivity
import com.orioltobar.androidklean.di.ViewModelFactory
import com.orioltobar.commons.Response
import com.orioltobar.commons.either
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.features.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val layoutId: Int get() = R.layout.activity_main

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)

        // TODO: Remove, this is only a test.
        vm.execute()
        vm.movieUiModel.observe(this, Observer<MovieModel>(::displayTestData))

        vm.movieModelFlow.observe(this, Observer<Response<MovieModel>> { response ->
            response.either(onSuccess = {
                textView2.text = it.tittle
            }, onFailure = {
                textView2.text = "ERROR"
            })
        })
    }

    private fun displayTestData(data: MovieModel) {
        textView.text = data.tittle
    }
}
