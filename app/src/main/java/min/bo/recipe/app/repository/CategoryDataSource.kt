package min.bo.recipe.app.repository

import min.bo.recipe.app.Banner

interface CategoryDataSource {

    suspend fun getCategories():List<Banner>
}