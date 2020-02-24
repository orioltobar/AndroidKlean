package com.orioltobar.networkdatasource.api.mappers

import com.orioltobar.networkdatasource.api.models.MovieGenreDetailApiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieGenreDetailMapperTest {

    private val mapper = MovieGenreDetailMapper()

    @Test
    fun `Map non-null received API values`() {
        val apiModel = MovieGenreDetailApiModel(12, "Action")

        val model = mapper.map(apiModel)

        assertEquals(apiModel.id, model.id)
        assertEquals(apiModel.name, model.name)
    }

    @Test
    fun `Map null received API values to default ones`() {
        val model = mapper.map(MovieGenreDetailApiModel(null, null))

        assertEquals(-1, model.id)
        assertEquals("", model.name)
    }
}