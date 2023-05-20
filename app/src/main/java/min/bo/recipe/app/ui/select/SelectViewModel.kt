package min.bo.recipe.app.ui.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import min.bo.recipe.app.Banner
import min.bo.recipe.app.model.Title
import min.bo.recipe.app.repository.SelectRepository

class SelectViewModel(private val selectRepository: SelectRepository) : ViewModel(){


    private val _title = MutableLiveData<Title>()
    val title: LiveData<Title> = _title


    private val _topBanners = MutableLiveData<List<Banner>>()
    val topBanners: LiveData<List<Banner>> = _topBanners

    init{
        loadSelectData()
    }

    private fun loadSelectData(){
        val selectData = selectRepository.getSelectData()
        selectData?.let{selectData->
            _title.value = selectData.title
            _topBanners.value = selectData.topBanners

        }
    }

}