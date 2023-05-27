package min.bo.recipe.app.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import min.bo.recipe.app.model.CerealData
import min.bo.recipe.app.repository.ListRepository
import min.bo.recipe.app.ui.common.Event

class ListViewModel(
    private val listRepository:ListRepository
):ViewModel() {


    private val _items = MutableLiveData<List<CerealData>>()
    val items:LiveData<List<CerealData>> = _items


    private val _openCerealEvent = MutableLiveData<Event<CerealData>>()
    val openCerealEvent:LiveData<Event<CerealData>> = _openCerealEvent

    init{
        loadList()
    }

    fun openCerealDetail(Cereal:CerealData){
        _openCerealEvent.value = Event(Cereal)
    }

    private fun loadList(){
        viewModelScope.launch {
            val lists = listRepository.getLists()

            _items.value = lists
        }
        // TODO repository에 데이터 요청
    }
}