package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class GetSerieAddedUseCase(val fitnFlowRepository: FitnFlowRepository) : UseCase<GetSerieAddedParam, List<SerieModel>>(){
    override fun run(params: GetSerieAddedParam): Flow<List<SerieModel>> = flow {
        val serieAdded = fitnFlowRepository.getSerieListOfExerciseAdded(params.exerciseId)
        emit(serieAdded)
    }
}
data class GetSerieAddedParam(val exerciseId: Int)