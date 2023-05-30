package min.bo.recipe.app.network

import min.bo.recipe.app.model.CerealData
import min.bo.recipe.app.model.LogData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiClient3 {



    @GET("log_list.json")
    suspend fun getLogs(): List<LogData>



    companion object {



        private const val baseUrl = "https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/"
        fun create():ApiClient3{

            val logger = HttpLoggingInterceptor().apply{
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient3::class.java)
        }
    }
}