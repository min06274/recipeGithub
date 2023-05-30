package min.bo.recipe.app.ui.log

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import min.bo.recipe.app.model.LogData
import min.bo.recipe.app.repository.LogRepository
import min.bo.recipe.app.ui.common.Event

class LogViewModel(
    private val logRepository:LogRepository
):ViewModel() {



    private val _items = MutableLiveData<List<LogData>>()
    val items:LiveData<List<LogData>> = _items

    private val _openLogEvent = MutableLiveData<Event<LogData>>()
    val openLogEvent:LiveData<Event<LogData>> = _openLogEvent

    init{
        loadLog()
    }


    fun openLogDetail(Log:LogData){
        _openLogEvent.value = Event(Log)
    }

    private fun loadLog(){

        viewModelScope.launch {
            val logs = logRepository.getLogs()
            _items.value = logs
        }
    }
}