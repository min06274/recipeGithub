package min.bo.recipe.app.repository

import min.bo.recipe.app.model.SelectData

interface SelectDataSource {

    suspend fun getSelectData():SelectData?
}