package com.example.viittomatulkki


import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import java.io.File

class MachineLearning() {
    companion object {
        private const val TAG = "MachineLearning"

    }


    fun processVideo(dataPath: String): String{
        Log.d(TAG,"Ollaan Machine Learningissa")

        //Tässä mallin lautaus ja käyttö
        Log.d(TAG,"ML saatiin: $dataPath")

        return ""
    }


    fun processImage(dataPath: String): String{
        Log.d(TAG,"Ollaan Machine Learningissa")

        //Tässä mallin lautaus ja käyttö
        Log.d(TAG,"ML saatiin: $dataPath")

        sendToServerPicture(dataPath)

        return ""
    }


    private fun sendToServerPicture(path: String){
        //Tämä ja alla funktion POST requestit saatu: https://fuel.gitbook.io/documentation/core/fuel otsikon Using multipart/form-data (UploadRequest) alta
        Fuel.upload("https://www.ipt.oamk.fi/linux186/vastaanotin.php")
            .add { FileDataPart(File(path), name = "uploaded_file", filename="image.jpg") }
            .response { result -> Log.d(TAG, result.toString())
            }

    }

    private fun sendToServerVideo(path: String){

        Fuel.upload("https://www.ipt.oamk.fi/linux186/vastaanotin.php")
            .add { FileDataPart(File(path), name = "uploaded_file", filename="video.mp4") }
            .response { result ->
                Log.d(TAG, result.toString())
            }
    }

}
