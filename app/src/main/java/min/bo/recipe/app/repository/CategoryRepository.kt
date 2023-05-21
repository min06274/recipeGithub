package min.bo.recipe.app.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import min.bo.recipe.app.Banner

class CategoryRepository(
    private val remoteDataSource: CategoryRemoteDataSource
) {


    suspend fun getCategories():List<Banner>{
        return remoteDataSource.getCategories()
    }
}