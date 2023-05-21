package min.bo.recipe.app.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import min.bo.recipe.app.Banner
import min.bo.recipe.app.repository.CategoryRepository

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
):ViewModel() {

    private val _items = MutableLiveData<List<Banner>>()
    val items:LiveData<List<Banner>> = _items


    init{
        loadCategory()
    }
    private fun loadCategory(){

        viewModelScope.launch {
            val categories= categoryRepository.getCategories()
            _items.value = categories

        }
    }
}