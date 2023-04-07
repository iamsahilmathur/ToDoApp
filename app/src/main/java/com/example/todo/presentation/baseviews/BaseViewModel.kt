package com.example.todo.presentation.baseviews
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.core.utilities.Event

abstract class BaseViewModel : ViewModel(){

    protected val _showSnackBar = MutableLiveData<Event<String>>()
    val showSnackbar : LiveData<Event<String>> get() = _showSnackBar
}