package min.bo.recipe.app.repository

import min.bo.recipe.app.model.LogData

interface LogDataSource {

    suspend fun getLogs():List<LogData>
}