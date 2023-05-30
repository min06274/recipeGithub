package min.bo.recipe.app.repository

import min.bo.recipe.app.model.LogData

class LogRepository(
    private val remoteDataSource:LogRemoteDataSource
) {

    suspend fun getLogs():List<LogData>{
        return remoteDataSource.getLogs()
    }
}