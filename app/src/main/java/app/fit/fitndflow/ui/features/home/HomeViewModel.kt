package app.fit.fitndflow.ui.features.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import app.fit.fitndflow.domain.Utils
import app.fit.fitndflow.domain.common.arq.FitRxObserver
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.model.UserModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.GetTrainingUseCase
import app.fit.fitndflow.domain.usecase.GetTrainingUseCaseParams
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase
import app.fit.fitndflow.domain.usecase.RegisterUserUseCaseParams
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class HomeViewModel : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    private var date: Date = Date()
    private val fitnFlowRepository: FitnFlowRepository = FitnFlowRepositoryImpl.getInstance()

    fun dayBefore() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        date = calendar.time
        viewModelScope.launch {
            _state.emit(State.CurrentDateChanged(date))
        }
    }

    fun dayAfter() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, +1)
        date = calendar.time
        viewModelScope.launch {
            _state.emit(State.CurrentDateChanged(date))
        }
    }

    fun emitDate() {
        viewModelScope.launch { _state.emit(State.CurrentDateChanged(date)) }
    }

    fun requestRegisterEmptyUser(
        context: Context) {
        val emptyUserModel = RegisterUserUseCase(fitnFlowRepository, context)
        val params = RegisterUserUseCaseParams(null, null, null)
        viewModelScope.launch {
            emptyUserModel(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.FullScreenError) }
                .collect { _state.emit(State.RegisterCompleted) }
        }
    }


    fun requestTrainingFromModel(context: Context) {
        val date: String = Utils.getEnglishFormatDate(date)
        val getTrainingUseCase = GetTrainingUseCase(fitnFlowRepository, context)
        val params = GetTrainingUseCaseParams(date)
        viewModelScope.launch {
            getTrainingUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.FullScreenError) }
                .collect{_state.emit(State.TrainingListRecived(it))}
        }
    }
}

sealed class State {
    object Loading : State()
    object RegisterCompleted : State()
    object FullScreenError : State()
    data class CurrentDateChanged(val date: Date) : State()
    data class TrainingListRecived(val categoryList: List<CategoryModel>) : State()
}