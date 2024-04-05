package app.fit.fitndflow.domain.model

import app.fit.fitndflow.data.dto.exercises.RepsAndWeightResponseDto
import java.io.Serializable

data class ExerciseModel(val id: Int? = null, val name: String, var serieList: MutableList<SerieModel>? = null, var lastFirstSerie: SerieModel?, var record: SerieModel?): Serializable
