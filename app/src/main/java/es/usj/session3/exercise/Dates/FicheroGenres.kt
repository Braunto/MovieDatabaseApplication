package es.usj.session3.exercise.Dates

import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class FicheroGenres {
    companion object {

        internal lateinit var hola: String


        fun GsonGenres(serverGen: String) {


            var urlGenres = URL("$serverGen")
            var urlConnection = urlGenres.openConnection() as
                    HttpURLConnection
            var inputStreamGenres = BufferedInputStream(urlConnection.getInputStream())
            //val result =readStream(inputStream)
            val jsonStringGenres = readStream(inputStreamGenres)
            val gson = Gson()
            val genresArray: Array<Genre> = gson.fromJson(
                jsonStringGenres,
                Array<Genre>::class.java
            )

            FicheroGenres.Store(jsonStringGenres)




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