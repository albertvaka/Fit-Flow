package app.fit.fitndflow.ui.features.categories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import app.fit.fitndflow.data.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.common.arq.FitObserver;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import app.fit.fitndflow.domain.usecase.AddCategoryUseCase;
import app.fit.fitndflow.domain.usecase.AddExerciseUseCase;
import app.fit.fitndflow.domain.usecase.DeleteCategoryUseCase;
import app.fit.fitndflow.domain.usecase.GetCategoriesUseCase;
import app.fit.fitndflow.domain.usecase.ModifyCategoryUseCase;

public class CategoriesAndExercisesViewModel extends ViewModel {
    private FitnFlowRepository fitnFlowRepository = new FitnFlowRepositoryImpl();
    private MutableLiveData<CategoryModel> actualCategory = new MutableLiveData<>();
    private MutableLiveData<List<CategoryModel>> mutableCategoryList = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseModel>> mutableExerciseList = new MutableLiveData<>();

    private MutableLiveData<Boolean> mutableSlideError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> mutableFullScreenError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isSaveSuccess = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isDeleteSuccess = new MutableLiveData<>(false);

    private String lastName;

    /*Getters*
     *
     * */


    public MutableLiveData<Boolean> getIsDeleteSuccess() {return isDeleteSuccess;}

    public MutableLiveData<CategoryModel> getActualCategory() {
        return actualCategory;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsSaveSuccess() {return isSaveSuccess; }

    public MutableLiveData<List<CategoryModel>> getMutableCategoryList() {
        return mutableCategoryList;
    }
    public MutableLiveData<List<ExerciseModel>> getMutableExerciseList(){
        return mutableExerciseList;
    }

    public MutableLiveData<Boolean> getMutableSlideError() {
        return mutableSlideError;
    }

    public MutableLiveData<Boolean> getMutableFullScreenError() {
        return mutableFullScreenError;
    }


    public String getLastName() {
        return lastName;
    }

    /*End Getters*
     *
     * */
    public void requestCategoriesFromModel(Context context) {
            GetCategoriesUseCase getCategoriesUseCase = new GetCategoriesUseCase(context, fitnFlowRepository);
        getCategoriesUseCase.execute(new FitObserver<List<CategoryModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                mutableFullScreenError.setValue(false);
                isLoading.setValue(true);
            }

            @Override
            public void onSuccess(List<CategoryModel> categoryModels) {
                mutableCategoryList.setValue(categoryModels);
                mutableFullScreenError.setValue(false);
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableFullScreenError.setValue(true);
                isLoading.setValue(false);
            }
        });
    }

    public void modifyCategory(Context context, String language, String categoryName, int id){
        new ModifyCategoryUseCase(context, language, categoryName, id, fitnFlowRepository).execute(new FitObserver<List<CategoryModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                mutableSlideError.setValue(false);
                isSaveSuccess.setValue(false);
            }
            @Override
            public void onSuccess(List<CategoryModel> categoryModelList) {
                mutableCategoryList.setValue(categoryModelList);
                isLoading.setValue(false);
                isSaveSuccess.setValue(true);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
                isSaveSuccess.setValue(false);
            }
        });
    }

    public void addNewCategory(Context context, String language, String nameCategory){
        new AddCategoryUseCase(context, language, nameCategory, fitnFlowRepository). execute(new FitObserver<List<CategoryModel>>() {

            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                isSaveSuccess.setValue(false);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onSuccess(List<CategoryModel> categoryModelList) {
                mutableCategoryList.setValue(categoryModelList);
                isLoading.setValue(false);
                isSaveSuccess.setValue(true);
                mutableSlideError.setValue(false);
                lastName = null;

            }
            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
                isSaveSuccess.setValue(false);
                lastName = nameCategory;
            }
        });
    }
    public void addNewExercise(Context context, String language, String nameExercise){
        new AddExerciseUseCase(context, language, nameExercise, actualCategory.getValue().getId(), fitnFlowRepository).execute(new FitObserver<List<ExerciseModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                isSaveSuccess.setValue(false);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onSuccess(List<ExerciseModel> exerciseModels) {
                mutableExerciseList.setValue(exerciseModels);
                isLoading.setValue(false);
                isSaveSuccess.setValue(true);
                mutableSlideError.setValue(false);
                lastName = null;
            }

            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
                isSaveSuccess.setValue(false);
                lastName = nameExercise;
            }
        });
    }

    public void deleteCategory(int id, Context context){
        new DeleteCategoryUseCase(id, context, fitnFlowRepository).execute(new FitObserver<List<CategoryModel>>() {

            @Override
            protected void onStart(){
                super.onStart();
                isLoading.setValue(true);
                mutableSlideError.setValue(false);
            }

            @Override
            public void onSuccess(List<CategoryModel> categoryModelList) {
                isLoading.setValue(false);
                isDeleteSuccess.setValue(true);
                mutableCategoryList.setValue(categoryModelList);
                mutableSlideError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
            }
        });
    }
}
