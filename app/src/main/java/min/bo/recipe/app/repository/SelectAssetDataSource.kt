package min.bo.recipe.app.repository

import com.google.gson.Gson
import min.bo.recipe.app.AssetLoader
import min.bo.recipe.app.model.SelectData

class SelectAssetDataSource(private val assetLoader: AssetLoader):SelectDataSource {
    private val gson = Gson()


    override fun getSelectData(): SelectData? {

        return assetLoader.getJsonString("select.json")?.let{selectJsonString->
            gson.fromJson(selectJsonString, SelectData::class.java)
        }

    }
}

