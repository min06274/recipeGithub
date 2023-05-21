package min.bo.recipe.app.repository

import com.google.gson.Gson
import min.bo.recipe.app.AssetLoader
import min.bo.recipe.app.Banner
import min.bo.recipe.app.model.SelectData
import min.bo.recipe.app.network.ApiClient

class SelectAssetDataSource(private val apiClient: ApiClient):SelectDataSource {
    override suspend fun getSelectData(): SelectData? {

        return apiClient.getCategories()
    }

}

