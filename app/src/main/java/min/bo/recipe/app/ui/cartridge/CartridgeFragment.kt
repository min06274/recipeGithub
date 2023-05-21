package min.bo.recipe.app.ui.cartridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import min.bo.recipe.app.R

class CartridgeFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cartridge,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val jsonButton: Button = view.findViewById(R.id.json_btn)
        
        
        jsonButton.setOnClickListener{
            jsonButton.text = "이이이"
        }





    }

}