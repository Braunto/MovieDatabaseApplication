package es.usj.session3.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.content.Intent
import android.widget.Toast
import es.usj.session3.exercise.Dates.FIcheroPeliculas
import es.usj.session3.exercise.Dates.FicheroActors
import es.usj.session3.exercise.Dates.FicheroGenres
import es.usj.session3.exercise.Dates.movie
import kotlinx.android.synthetic.main.activity_main.*


const val SERVER = "http://10.0.2.2/APPMOviles/mobile/user/getMovies.php?user=braun&pass=1234"

const val ActorServer="http://10.0.2.2/APPMOviles/mobile/user/getActors.php?user=braun&pass=1234"

const val GenresServer="http://10.0.2.2/APPMOviles/mobile/user/getGenres.php?user=braun&pass=1234"

const val UpdateServer="http://10.0.2.2/APPMOviles/mobile/user/updateMovie.php?user=braun&pass=1234"

const val AddServer="http://10.0.2.2/APPMOviles/mobile/user/addMovie.php?user=braun&pass=1234"

const val OtherServer="http://www.omdbapi.com/?t=passenger&apikey=e332394a"


class MainActivity : AppCompatActivity() {


//aqui


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var url = URL("$SERVER")
        var urlConnection = url.openConnection() as
                HttpURLConnection
        Thread {
            try {

                var inputStream =
                    BufferedInputStream(urlConnection.getInputStream())
                //val result =readStream(inputStream)
                val jsonString = readStream(inputStream)
                val gson= Gson()
                val movieArray: Array<movie> = gson.fromJson(
                    jsonString,
                    Array<movie>::class.java
                )

                FIcheroPeliculas.Store(jsonString)



                FicheroActors.PassGson("$ActorServer")
                FicheroGenres.GsonGenres("$GenresServer")



                /*runOnUiThread({

                        tvMovie.text=jsonString
                    //tvMovie.text = FicheroGenres.hola;
                    //tvMovie.text=FicheroActors.hola
                    val toast: Toast = Toast.makeText(getApplicationContext(), FicheroActors.hola, Toast.LENGTH_SHORT)
                    toast.show()

                })
                    */




                val intent = Intent(baseContext, MovieList::class.java)

                startActivity(intent);



                //Log.e("CONTENT",result)
            } finally {
                urlConnection.disconnect()
            }
        }.start()


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




