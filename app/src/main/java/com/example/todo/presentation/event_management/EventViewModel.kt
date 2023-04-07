package com.example.todo.presentation.event_management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.core.utilities.Constants
import com.example.todo.core.utilities.Event
import com.example.todo.core.utilities.Resource
import com.example.todo.domain.models.EventModel
import com.example.todo.domain.usecases.AddEventUseCase
import com.example.todo.domain.usecases.UpdateEventUseCase
import com.example.todo.presentation.baseviews.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase
) : BaseViewModel() {

    private val _addMutableLiveData = MutableLiveData<Resource<EventModel>>()
    val addLiveData: LiveData<Resource<EventModel>> get() = _addMutableLiveData

    val eName = MutableLiveData("")
    val eDesc = MutableLiveData("")

    var isUpdate: String? = null
    var id: Int = 0


    fun getStatus(action: String, eId: Int = 0) {
        isUpdate = action
        id = eId
    }


    fun addEvent() {
        viewModelScope.launch {
            if (eName.value!!.isEmpty() || eDesc.value!!.isEmpty()) {
                _showSnackBar.value = Event("All fields are required")
            } else {
                val eventModel =
                    EventModel(id = id, eventName = eName.value!!, eventDesc = eDesc.value!!)
                withContext(IO) {
                    _addMutableLiveData.postValue(Resource.Loading())

                    var status: Any? = null

                    status = if (isUpdate.equals(Constants.UPDATE_CONST)) {
                        updateEventUseCase.updateEvent(eventModel)
                    } else {
                        addEventUseCase.addEvent(eventModel)
                    }

                    if (status != null) {
                        withContext(Main) {
                            eName.value = ""
                            eDesc.value = ""
                        }

                        if (isUpdate.equals(Constants.UPDATE_CONST))
                            _addMutableLiveData.postValue(Resource.Success(message = "Event Updated"))
                        else
                            _addMutableLiveData.postValue(Resource.Success(message = "Event Added"))

                    } else {
                        _addMutableLiveData.postValue(
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

}