package min.bo.recipe.app.repository

import min.bo.recipe.app.Banner
import min.bo.recipe.app.network.ApiClient

class CategoryRemoteDataSource(private val apiClient:ApiClient):CategoryDataSource {
    override suspend fun getCategories(): List<Banner> {

        return apiClient.getCategories()
    }


}