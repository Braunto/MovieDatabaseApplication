package es.usj.session3.exercise

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import es.usj.session3.exercise.Dates.movie
import kotlinx.android.synthetic.main.activity_view_movie.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ViewMovie : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)

        val gson= Gson()


       val  mov= gson.fromJson<movie>(intent.getStringExtra("movie"), movie::class.java)

        //Intento de meter imagen pelicula

        var url = URL("http://www.omdbapi.com/?t="+mov.title+"&apikey=e332394a")
        var urlConnection = url.openConnection() as
                HttpURLConnection
        Thread {
            try {

                var inputStream =
                    BufferedInputStream(urlConnection.getInputStream())
                //val result =readStream(inputStream)
                val jsonString = readStream(inputStream)

                val createbuild = GsonBuilder()
                val imagephoto: Any = createbuild.create().fromJson(jsonString, Any::class.java)

                val oMap = (imagephoto as Map<*, *>)

                val verification: String=oMap.get("Response").toString()

                if(verification!="False") {
                    val urlphoto = URL((oMap.get("Poster") as String?))
                    val bmp = BitmapFactory.decodeStream(urlphoto.openConnection().getInputStream())

                    runOnUiThread({


                        imageView.setImageBitmap(bmp)



                    })
                }



            } finally {
                urlConnection.disconnect()
            }
        }.start()










        tvTitle.setText("Title: "+mov.title)
        tvDirector.setText("Director: " +mov.director)
        tvRuntime.setText("Runtime: "+mov.runtime.toString())
        tvRating.setText("Rating: "+mov.rating.toString())
        tvYear.setText("Year: "+mov.year.toString())
        tvDescription.setText("Description: "+mov.description)



        btnActors.setOnClickListener() {

            val intent = Intent(this, ActorsView::class.java)
            intent.putExtra("actors", gson.toJson(mov))
            startActivity(intent)
        }


        btnGenres.setOnClickListener() {

            val intent = Intent(this, GenresView::class.java)
            intent.putExtra("genres", gson.toJson(mov))
            startActivity(intent)
        }









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
