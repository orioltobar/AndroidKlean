package com.orioltobar.networkdatasource.api.mappers

import com.orioltobar.commons.MockObjects
import com.orioltobar.networkdatasource.api.models.MovieGenresApiModel
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieGenresMapperTest {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    @MockK
    private lateinit var detailApiModel: MovieGenreDetailMapper

    private val moshi = Moshi.Builder().build()

    private val mapper by lazy {
        MovieGenresMapper(detailApiModel)
    }

    @Test
    fun `Map non-null received API values`() {
        val apiModel = moshi.adapter(MovieGenresApiModel::class.java).fromJson(MockObjects.genreJson)

        val model = mapper.map(apiModel)

        assertEquals(19, model.genre.size)
    }

    @Test
    fun `Map null received API values to default ones`() {
        val model = mapper.map(MovieGenresApiModel(null))

        assertEquals(0, model.genre.size)
    }
}