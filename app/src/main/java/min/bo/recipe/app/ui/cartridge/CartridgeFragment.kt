package min.bo.recipe.app.ui.cartridge

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import min.bo.recipe.app.R
import com.google.firebase.database.*
import min.bo.recipe.app.ml.Model
import min.bo.recipe.app.model.Cereal
import min.bo.recipe.app.model.CerealData
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.min


class CartridgeFragment:Fragment() {



    private lateinit var camera1: Button
    private lateinit var camera2: Button

    private lateinit var camera3: Button

    private lateinit var imageView: ImageView
    private lateinit var result: TextView
    private lateinit var bitmap:Bitmap
    private val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val myRef = database1.getReference("top_banners")
    private val database = FirebaseDatabase.getInstance("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val cerealsRef = database.getReference("cereals")

    private val database2 = FirebaseDatabase.getInstance("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val cerealListRef = database2.getReference("cereal_list")
    private val imageSize = 180




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cartridge,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        camera1 = view.findViewById(R.id.button1)


        result = view.findViewById(R.id.result)
        imageView = view.findViewById(R.id.imageView)






        camera1.setOnClickListener {
           val intent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResult.launch(intent)
        }



    }

    private fun classifyImage(image: Bitmap) {
        val model = Model.newInstance(requireContext())

// Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 180, 180, 3), DataType.FLOAT32)

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
        //val classes = arrayOf("Apple", "Banana", "Orange")
        val classes = arrayOf("그라놀리지 블랙세서미 스트리트 흑임자", "그라놀리지 카카올로지 그래놀라", "네이쳐패스 러브 크런치 베리", "롯데제과 퀘이커 오트밀 오리지널", "마켓오 그래놀라 다이제", "마켓오 그래놀라 단백질 넛츠 카라멜",
            "볼제너뮬러 유기농 초코크런치", "씨알로 우리쌀프레이크", "아몬드", "오그래 퐁", "오리온 미쯔 시리얼", "오리온 오 그래놀라 팝 초코 아몬드", "오리온 오 그래놀라 팝 현미아몬드",
            "위트빅스 오리지날", "제너럴밀스 시나몬 토스트 크런치'", "켈로그 고소한 오곡 푸레이크", "켈로그 담백한 현미 푸레이크", "켈로그 리얼 그래놀라", "켈로그 리얼 그래놀라 크런치오트",
            "켈로그 스페셜K 오리지널", "켈로그 첵스 초코 스노우 초코볼", "켈로그 첵스초코 레인보우", "켈로그 첵스초코 마시멜로", "켈로그 첵스초코 문앤스타", "켈로그 첵스초코 오리지널", "켈로그 코코팝스",
            "켈로그 콘푸로스트 라이트 슈거", "켈로그 콘프로스트", "켈로그 통귀리 그래놀라", "켈로그 프로틴 그래놀라 다크초코볼", "켈로그 허쉬 초코크런치", "켈로그 현미 푸레이크", "켈로그 후르트링",
            "포스트 건강한칠곡", "포스트 고소한 아몬드 후레이크", "포스트 그래놀라 크랜베리 아몬드", "포스트 듀오링", "포스트 라이스앤 단백질 후레이크", "포스트 오곡 코코볼", "포스트 오레오 오즈",
            "포스트 오레오오즈 민트초코", "포스트 오트밀 오리지널", "포스트 초코 후레이크", "포스트 코코 그래놀라", "포스트 코코아 페블", "포스트 콘푸라이트", "포스트 허니 오즈", "포스트 현미 그래놀라",
            "플라하반 아이리쉬 포리지 오트밀 시리얼", "플라하반 퀵 오트밀 오리지날")


        result.text = classes[maxPos]


        change_information(cerealsRef,cerealListRef,classes[maxPos])




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




private fun change_information(
    cerealsRef: DatabaseReference,
    cerealListRef: DatabaseReference,
    temp:String,
) {

    cerealsRef.orderByChild("name").equalTo(temp).addListenerForSingleValueEvent(object :
        ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {

            val childSnapshot = dataSnapshot.children.firstOrNull()

            childSnapshot?.let {
                val cerealIndex = it.key // Get the cereal index
                val brandname = it.child("name").value as? String
                val information = it.child("information").value as? String
                val kcal = it.child("kcal").value as? String
                val ceimage = it.child("img_url").value as? String
                val ceid = it.child("id").value as? String
                val spec = it.child("spec").value as? String
                val purchase = it.child("purchase_url").value as? String
                if (purchase != null && spec != null && brandname != null && information != null && ceimage != null && ceid != null && kcal != null) {



                    cerealListRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {


                            var isDuplicate = false

                            for (childSnapshot in dataSnapshot.children) {
                                val name = childSnapshot.child("name").value as? String
                                if (name == temp) {
                                    isDuplicate = true
                                    break
                                }
                            }

                            if (!isDuplicate) {
                                val count = dataSnapshot.childrenCount
                                val newCerealRef = cerealListRef.child((count).toString())
                                val newCereal = CerealData(
                                    cereal_image_url = ceimage,
                                    name = brandname,
                                    information = information,
                                    cereal_id = count.toString(),
                                    cereal_kcal = kcal,
                                    cereal_spec = spec,
                                    cereal_purchase_url = purchase
                                )
                                newCerealRef.setValue(newCereal)
                                println(count)
                            }
                            else {
                                println("Cereal already exists with the name: $temp")
                            }


                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            println("Failed to read cereals")
                        }
                    })


                    println(information)
                } else {
                    println("Failed to read cereal information for index: $cerealIndex")
                }
            } ?: run {
                println("No cereal found matching the condition")
            }

        }

        override fun onCancelled(databaseError: DatabaseError) {
            println("Failed to read cereals")
        }
    })
}
