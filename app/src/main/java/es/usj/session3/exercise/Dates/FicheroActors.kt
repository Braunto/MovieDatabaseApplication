package es.usj.session3.exercise.Dates

import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FicheroActors {
    companion object {

        internal lateinit var hola: String


        fun PassGson(serverAct: String) {


            var urlActors = URL("$serverAct")
            var urlConnection = urlActors.openConnection() as
                    HttpURLConnection
            var inputStreamActors = BufferedInputStream(urlConnection.getInputStream())
            //val result =readStream(inputStream)
            val jsonStringActors = readStream(inputStreamActors)
            val gson = Gson()
            val actorsArray: Array<Actor> = gson.fromJson(
                jsonStringActors,
                Array<Actor>::class.java
            )

            FicheroActors.Store(jsonStringActors)




        }


        fun Store(json: String) {
            hola = json
        }

        fun get(): String {
            return hola
        }


        fun readStream(inputStream: InputStream): String {
            val br = BufferedReader(InputStreamReader(inputStream))
            val total = StringBuilder()
            while (true) {
                val line = br.readLine() ?: break
                total.append(line).append('\n')
            }
            return total.toString()
        }
















    }
}