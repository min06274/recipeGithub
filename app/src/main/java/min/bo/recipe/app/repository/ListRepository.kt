package min.bo.recipe.app.repository

import kotlinx.coroutines.withContext
import min.bo.recipe.app.model.CerealData

class ListRepository(
    private val remoteDataSource: ListRemoteDataSource
) {
    suspend fun getLists():List<CerealData>{


        return remoteDataSource.getLists()
    }
}