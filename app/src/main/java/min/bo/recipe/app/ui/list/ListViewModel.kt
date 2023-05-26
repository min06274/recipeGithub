package min.bo.recipe.app.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import min.bo.recipe.app.model.CerealData
import min.bo.recipe.app.repository.ListRepository

class ListViewModel(
    private val listRepository:ListRepository
):ViewModel() {


    private val _items = MutableLiveData<List<CerealData>>()
    val items:LiveData<List<CerealData>> = _items


    init{
        loadList()
    }

    private fun loadList(){
        viewModelScope.launch {
            val lists = listRepository.getLists()

            _items.value = lists
        }
        // TODO repository에 데이터 요청
    }
}