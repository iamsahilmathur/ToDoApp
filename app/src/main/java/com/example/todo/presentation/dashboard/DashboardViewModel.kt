package com.example.todo.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.core.utilities.Constants
import com.example.todo.core.utilities.Event
import com.example.todo.core.utilities.Resource
import com.example.todo.domain.models.EventModel
import com.example.todo.domain.usecases.AllEventUseCases
import com.example.todo.domain.usecases.DeleteEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val allEventUseCases: AllEventUseCases,
    private val deleteEventUseCase: DeleteEventUseCase
) :
    ViewModel() {


    private val _state = MutableLiveData<Resource<List<EventModel>>>()
    val state: LiveData<Resource<List<EventModel>>> get() = _state

    private val _dELiveData = MutableLiveData<Resource<EventModel>>()
    val dELiveData: LiveData<Resource<EventModel>> get() = _dELiveData


    init {
        viewModelScope.launch {

            withContext(IO) {
                _state.postValue(Resource.Loading())
                allEventUseCases.getAllEvents()
                val list = allEventUseCases.getAllEvents()

                if (list.size > 0) {
                    _state.postValue(Resource.Success(data = list))
                } else {
                    _state.postValue(Resource.Error(data = null, message = "Events not found"))
                }
            }
        }
    }


    fun deleteEvent(eventModel: EventModel) {
        viewModelScope.launch {
            withContext(IO) {
                _dELiveData.postValue(Resource.Loading())

                val status = eventModel?.let { deleteEventUseCase.deleteEvent(it) }

                if (status != null) {
                    _dELiveData.postValue(Resource.Success(message = "Event Updated"))
                } else {
                    _dELiveData.postValue(
                        Resource.Error(
                            data = null,
                            message = "Something went wrong"
                        )
                    )
                }

            }
        }
    }

}