package app.fit.fitndflow.domain.usecase

import app.fit.fitndflow.domain.common.usecase.UseCase
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSerieUseCase @Inject constructor(val fitnFlowRepository: FitnFlowRepository): UseCase<GetSerieToDeleteParams, List<SerieModel>>(){
    override fun run(params: GetSerieToDeleteParams): Flow<List<SerieModel>> = flow {
        val serieDeleted = fitnFlowRepository.deleteSerie(params.serieId)
        fitnFlowRepository.updateCurrentTrainingListCache()
        emit(serieDeleted)
    }
}

data class GetSerieToDeleteParams(val serieId: Int)