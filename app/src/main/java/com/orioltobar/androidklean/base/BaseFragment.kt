package com.orioltobar.androidklean.base

import com.orioltobar.androidklean.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory
}