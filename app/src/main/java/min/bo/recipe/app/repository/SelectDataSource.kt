package min.bo.recipe.app.repository

import min.bo.recipe.app.model.SelectData

interface SelectDataSource {

    fun getSelectData():SelectData?
}