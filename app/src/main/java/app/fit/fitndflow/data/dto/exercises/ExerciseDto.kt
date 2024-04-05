package app.fit.fitndflow.data.dto.exercises

import app.fit.fitndflow.data.dto.StringInLanguagesDto
import app.fit.fitndflow.data.dto.trainings.SerieDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExerciseDto(@SerializedName("id") var id: Int?, @SerializedName("name") var exerciseName: StringInLanguagesDto?, @SerializedName("serieList") var serieList: List<SerieDto>?, @SerializedName("lastFirstSerie") var lastFirstSerie: RepsAndWeightResponseDto? = null, @SerializedName("maxPR") var record: RepsAndWeightResponseDto? = null): Serializable

data class RepsAndWeightResponseDto(@SerializedName("first") var reps: Int? = null, @SerializedName("second") var weight: Double? = null): Serializable