package min.bo.recipe.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import min.bo.recipe.app.R
import min.bo.recipe.app.common.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_main)
        bottomNavigationView.itemIconTintList = null


        val navController = supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()
        navController?.let{
            bottomNavigationView.setupWithNavController(it)
        }







    }


    fun setDataAtFragment(fragment:Fragment,first_gram: Int, second_gram: Int, third_gram: Int, carbo_gram: Double,
                          protein_gram: Double, fat_gram: Double, print_kcal: Int,
                          total_kcal: Double){
        println("메인에서 호출됨~~~~~")
        println(first_gram)
        val args = Bundle()
        args.putInt(KEY_CARTRIDGE_GRAM1, first_gram)
        args.putInt(KEY_CARTRIDGE_GRAM2, second_gram)
        args.putInt(KEY_CARTRIDGE_GRAM3, third_gram)
        args.putDouble(KEY_CARBO, carbo_gram)
        args.putDouble(KEY_PROTEIN, protein_gram)
        args.putDouble(KEY_FAT, fat_gram)
        args.putInt(KEY_PRINT_KCAL, print_kcal)
        args.putDouble(KEY_TOTAL_KCAL, total_kcal)

        fragment.arguments = args

    }
}