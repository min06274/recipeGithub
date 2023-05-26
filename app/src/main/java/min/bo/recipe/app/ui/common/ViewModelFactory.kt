package min.bo.recipe.app.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import min.bo.recipe.app.network.ApiClient
import min.bo.recipe.app.network.ApiClient2
import min.bo.recipe.app.repository.ListRemoteDataSource
import min.bo.recipe.app.repository.ListRepository
import min.bo.recipe.app.repository.SelectAssetDataSource
import min.bo.recipe.app.repository.SelectRepository
import min.bo.recipe.app.ui.list.ListViewModel
import min.bo.recipe.app.ui.select.SelectViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context:Context):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SelectViewModel::class.java) -> {
                val repository = SelectRepository(SelectAssetDataSource(ApiClient.create()))
                SelectViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ListViewModel::class.java)-> {
                val repository = ListRepository(ListRemoteDataSource(ApiClient2.create()))
                return ListViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}