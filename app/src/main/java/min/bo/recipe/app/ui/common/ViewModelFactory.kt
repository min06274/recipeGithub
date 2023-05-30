package min.bo.recipe.app.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import min.bo.recipe.app.network.ApiClient
import min.bo.recipe.app.network.ApiClient2
import min.bo.recipe.app.network.ApiClient3
import min.bo.recipe.app.repository.*
import min.bo.recipe.app.ui.list.ListViewModel
import min.bo.recipe.app.ui.log.LogViewModel
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
            modelClass.isAssignableFrom(LogViewModel::class.java)-> {
                val repository = LogRepository(LogRemoteDataSource(ApiClient3.create()))
                return LogViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}