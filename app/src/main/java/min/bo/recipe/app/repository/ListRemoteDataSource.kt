package min.bo.recipe.app.repository

import min.bo.recipe.app.model.CerealData
import min.bo.recipe.app.network.ApiClient2

class ListRemoteDataSource(private val apiClient2:ApiClient2):ListDataSource {
    override suspend fun getLists(): List<CerealData> {
        return apiClient2.getLists()
    }

}