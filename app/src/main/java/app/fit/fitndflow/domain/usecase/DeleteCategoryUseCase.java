package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class DeleteCategoryUseCase extends FitUseCase<CategoryModel, Boolean> {

    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public DeleteCategoryUseCase(CategoryModel inputParams, Context context, FitnFlowRepository fitnFlowRepository) {
        super(inputParams);
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }

    @Override
    public Single<Boolean> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try{
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                Boolean response = fitnFlowRepository.deleteCategoryAndExercises(inputParams.getId(), apiKey);
                emitter.onSuccess(response);

            } catch(Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
