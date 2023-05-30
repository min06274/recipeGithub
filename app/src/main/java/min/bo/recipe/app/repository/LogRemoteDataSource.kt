package min.bo.recipe.app.repository

import min.bo.recipe.app.model.LogData
import min.bo.recipe.app.network.ApiClient3

class LogRemoteDataSource(private val apiClient3:ApiClient3):LogDataSource {
    override suspend fun getLogs(): List<LogData> {

        return apiClient3.getLogs()

    }
}