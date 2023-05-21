package min.bo.recipe.app.ui.cartridge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import min.bo.recipe.app.R
import min.bo.recipe.app.ui.category.CategoryViewModel
import min.bo.recipe.app.ui.common.ViewModelFactory
import org.json.JSONObject
import java.io.File

class CartridgeFragment:Fragment() {

    private val viewModel: CategoryViewModel by viewModels{ViewModelFactory(requireContext())}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cartridge,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel.items.observe(viewLifecycleOwner){
            Log.d("CategoryFragment","items = $it")
        }




    }

}