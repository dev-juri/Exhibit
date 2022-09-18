package com.oluwafemi.exhibit.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oluwafemi.exhibit.data.Exhibit
import com.oluwafemi.exhibit.data.ExhibitLoader
import com.oluwafemi.exhibit.data.RestExhibitLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: ExhibitLoader) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val error get() = _errorMessage

    private val _dataStatus = MutableLiveData(Status.NULL)
    val dataStatus get() = _dataStatus

    private val _exhibits = MutableLiveData<List<Exhibit>>()
    val exhibit: LiveData<List<Exhibit>> get() = _exhibits

    init {
        fetchExhibits()
    }

    fun fetchExhibits() {
        viewModelScope.launch {
            _dataStatus.postValue(Status.LOADING)

            try {
                repo.getExhibitList()
                    .collect { result ->
                        if (result.isEmpty()) {
                            _dataStatus.postValue(Status.ERROR)
                            _errorMessage.postValue("Something went wrong, please check your connection and try again.")
                        } else {
                            _dataStatus.postValue(Status.SUCCESS)
                            _exhibits.postValue(result)
                        }
                    }
            } catch (e: Exception) {
                _dataStatus.postValue(Status.ERROR)
                _errorMessage.postValue("Something went wrong, please check your connection and try again.")
            }
        }
    }

    fun clearExhibits() {
        _exhibits.postValue(emptyList())
    }

    fun resetStatus() {
        _dataStatus.postValue(Status.NULL)
    }
}

enum class Status {
    SUCCESS, ERROR, LOADING, NULL
}