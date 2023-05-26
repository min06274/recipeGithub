package min.bo.recipe.app.repository

import min.bo.recipe.app.model.CerealData

interface ListDataSource {
    suspend fun getLists(): List<CerealData>
}