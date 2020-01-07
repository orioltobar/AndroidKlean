package com.orioltobar.androidklean

import android.annotation.SuppressLint
import android.os.Bundle
import com.orioltobar.androidklean.base.BaseActivity
import com.orioltobar.androidklean.di.modules.AppModule.Companion.NAME_BASE_URL
import com.orioltobar.commons.Success
import com.orioltobar.domain.usecases.GetMovieUseCase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity() {

    @Inject
    @Named(NAME_BASE_URL)
    lateinit var testString: String

    @Inject
    lateinit var getMovieUseCase: GetMovieUseCase

    private val job = SupervisorJob()

    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    override val layoutId: Int get() = R.layout.activity_main

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textView.text = testString

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                getMovieUseCase.execute(550)
            }

            if (response is Success) {
                val movieText = "${response.result.originalTitle} ${response.result.id}"
                textView.text = movieText
            }
        }
    }
}
