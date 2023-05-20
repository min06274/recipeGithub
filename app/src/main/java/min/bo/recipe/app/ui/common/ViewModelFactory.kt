package min.bo.recipe.app.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import min.bo.recipe.app.AssetLoader
import min.bo.recipe.app.repository.SelectAssetDataSource
import min.bo.recipe.app.repository.SelectRepository
import min.bo.recipe.app.ui.select.SelectViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context:Context):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SelectViewModel::class.java)){
            val repository = SelectRepository(SelectAssetDataSource(AssetLoader(context)))
            return SelectViewModel(repository) as T
        }else{
            throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
        }
    }
}