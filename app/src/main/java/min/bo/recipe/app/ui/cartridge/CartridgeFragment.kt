package min.bo.recipe.app.ui.cartridge

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import min.bo.recipe.app.R
import min.bo.recipe.app.model.Cereal
import min.bo.recipe.app.ui.common.ViewModelFactory
import java.util.jar.Manifest
import androidx.core.app.ActivityCompat
import min.bo.recipe.app.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.min


class CartridgeFragment:Fragment() {

    val CAMERA = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99

    private lateinit var camera: Button
    private lateinit var gallery: Button
    private lateinit var imageView: ImageView
    private lateinit var result: TextView
    private lateinit var bitmap:Bitmap
    private val imageSize = 32

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cartridge,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




/*
        val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef = database1.getReference("top_banners")



        val database = FirebaseDatabase.getInstance("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val cerealsRef = database.getReference("cereals")

        val jsonButton: Button = view.findViewById(R.id.json_btn)

        jsonButton.setOnClickListener {
            //change_information(cerealsRef, myRef)

            addCereals(cerealsRef)
*/

        camera = view.findViewById(R.id.button)
        result = view.findViewById(R.id.result)
        imageView = view.findViewById(R.id.imageView)





        camera.setOnClickListener {
           val intent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResult.launch(intent)
        }


    }

    private fun classifyImage(image: Bitmap) {
        val model = Model.newInstance(requireContext())

// Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)

        val byteBuffer :ByteBuffer = ByteBuffer.allocateDirect(4*imageSize*imageSize*3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0

        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat(((`val` shr 16) and 0xFF).toFloat() * (1f / 1))
                byteBuffer.putFloat(((`val` shr 8) and 0xFF).toFloat() * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF).toFloat() * (1f / 1))
            }
        }



        inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        // Find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf("Apple", "Banana", "Orange")
        result.text = classes[maxPos]


// Releases model resources if no longer used.
        model.close()
    }



    private val activityResult:ActivityResultLauncher<Intent> =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK) {
            val extras = it.data!!.extras

            val image = extras?.get("data") as Bitmap
            val dimension = min(image?.width ?: 0, image?.height ?: 0)
            val thumbnail = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            imageView.setImageBitmap(thumbnail)

            val scaledImage = Bitmap.createScaledBitmap(thumbnail, imageSize, imageSize, false)
            classifyImage(scaledImage)
        }
    }



}





    private fun addCereals(cerealsRef: DatabaseReference){
        val cereal1 = Cereal(
            "1번시리얼",
            "30g당 탄수 5g"
        )

        cerealsRef.child("1").setValue(cereal1)
    }

    private fun change_information(
        cerealsRef: DatabaseReference,
        myRef: DatabaseReference
    ) {
        cerealsRef.child("0").child("information").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val information = task.result?.value as? String
                myRef.child("0").child("product_detail").child("information").setValue(information)
                println(information)
            } else {
                println("Failed to read cereal information")
            }
        }
    }

