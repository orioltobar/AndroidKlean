package com.orioltobar.diskdatasource.mappers

import com.orioltobar.diskdatasource.models.MovieGenreDbModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import org.junit.Assert
import org.junit.Test

class MovieGenreDbMapperTest {

    private val mapper = MovieGenreDbMapper()

    @Test
    fun `Map non-null received API values`() {
        val dbModel = MovieGenreDbModel(12, "Action", "", 12345L)

        val model = mapper.map(dbModel)

        Assert.assertEquals(dbModel.id, model.id)
        Assert.assertEquals(dbModel.name, model.name)
    }

    @Test
    fun `MapToDb non-null received API values`() {
        val model = MovieGenreDetailModel(12, "Action", "")

        val dbModel = mapper.mapToDbModel(model)

        Assert.assertEquals(dbModel.id, model.id)
        Assert.assertEquals(dbModel.name, model.name)
    }

    @Test
    fun `Map null received API values to default ones`() {
        val model = mapper.map(null)

        Assert.assertEquals(-1, model.id)
        Assert.assertEquals("", model.name)
    }


}