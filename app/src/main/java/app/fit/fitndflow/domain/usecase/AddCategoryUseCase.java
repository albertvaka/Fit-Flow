package app.fit.fitndflow.domain.usecase;

import static app.fit.fitndflow.domain.Utils.convertToStringInLanguages;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.dto.StringInLanguagesDto;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class AddCategoryUseCase extends FitUseCase<StringInLanguagesDto, List<CategoryModel>> {
    public static final String SPANISH = "es";
    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public AddCategoryUseCase(Context context, String language, String nameCategory, FitnFlowRepository fitnFlowRepository) {
        super(convertToStringInLanguages(language, nameCategory));
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }

    @Override
    public Single<List<CategoryModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try {
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<CategoryModel> response = fitnFlowRepository.addNewCategory(inputParams, apiKey);
                emitter.onSuccess(response);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
