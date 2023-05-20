package min.bo.recipe.app.repository

import min.bo.recipe.app.model.SelectData

class SelectRepository(private val assetDataSource: SelectAssetDataSource)
{


    fun getSelectData():SelectData?{
        return assetDataSource.getSelectData()
    }
}