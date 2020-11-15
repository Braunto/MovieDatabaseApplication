package es.usj.session3.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.Gson
import es.usj.session3.exercise.Dates.*
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.activity_edit_movie.*
import kotlinx.android.synthetic.main.activity_edit_movie.etDescription
import kotlinx.android.synthetic.main.activity_edit_movie.etDirector
import kotlinx.android.synthetic.main.activity_edit_movie.etFilm
import kotlinx.android.synthetic.main.activity_edit_movie.etRating
import kotlinx.android.synthetic.main.activity_edit_movie.etRevenue
import kotlinx.android.synthetic.main.activity_edit_movie.etRuntime
import kotlinx.android.synthetic.main.activity_edit_movie.etVotes
import kotlinx.android.synthetic.main.activity_edit_movie.etYear
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class AddMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)


        val allmovies:String=FIcheroPeliculas.get();


        val gson= Gson()
        val movieArray: Array<movie> = gson.fromJson(
            allmovies,
            Array<movie>::class.java
        )


        val indice=movieArray.size+1


        etVotes.setText(indice.toString())







        val allActors: String = FicheroActors.get();


        val gsondos = Gson()
        val ActorArray: Array<Actor> = gsondos.fromJson(
            allActors,
            Array<Actor>::class.java
        )


        val datos: MutableList<Actor>;
        datos = ActorArray.toMutableList();


        var ArrayNamesActors = arrayOf(String())

        for (i in 0..ActorArray.size - 1) {


            ArrayNamesActors = ArrayNamesActors + datos.get(i).name


        }



        if (spinnerActorsAdd != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, ArrayNamesActors
            )
            spinnerActorsAdd.adapter = adapter
        }



        //Meter el spinner con generos
        val allGenres: String = FicheroGenres.get();



        val GenresArray: Array<Genre> = gson.fromJson(
            allGenres,
            Array<Genre>::class.java
        )


        val datosGenres: MutableList<Genre>;
        datosGenres = GenresArray.toMutableList();


        var ArrayNamesGenres = arrayOf(String())

        for (i in 0..GenresArray.size - 1) {


            ArrayNamesGenres = ArrayNamesGenres + datosGenres.get(i).name


        }



        if (spinnerGenresAdd != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, ArrayNamesGenres
            )
            spinnerGenresAdd.adapter = adapter
        }






















        btnAdd.setOnClickListener() {






            var idActor = 1
            var idGenres = 1


            //Meter si no es seleccionado

            if (spinnerActorsAdd.getSelectedItem() != "") {


                for (i in 0..ArrayNamesActors.size - 1) {


                    if (spinnerActorsAdd.getSelectedItem() == datos.get(i).name) {

                        idActor = datos.get(i).id
                        break

                    }


                }


            }



            if (spinnerGenresAdd.getSelectedItem() != "") {



                for (i in 0..ArrayNamesGenres.size - 1) {


                    if (spinnerGenresAdd.getSelectedItem() == datosGenres.get(i).name) {

                        idGenres = datosGenres.get(i).id
                        break

                    }


                }



            }


            var      movieEdited = """{
                                        "id": """" + indice + """",
                                        "title": """" + etFilm.text + """",
                                        "description": """" + etDescription.text + """",
                                        "director": """" + etDirector.text + """",
                                        "year": """" + etYear.text + """",
                                        "runtime": """" + etRuntime.text + """",
                                        "rating": """" + etRating.text + """",
                                        "votes": """" + etVotes.text + """",
                                        "revenue": """" + etRevenue.text + """",
                                        "genres": [
                                          """" + idGenres + """"
                                        ],
                                        "actors": [
                                          """" + idActor + """"
                                        ]
                                        }
                                        """




            Thread {
                try {
                    post("$AddServer", movieEdited);
                    val intent = Intent(baseContext, MainActivity::class.java)

                    startActivity(intent);

                    finish()


                    //Log.e("CONTENT",result)
                } finally {

                }
            }.start()


        }




    }




    fun post(url: String, body: String): String {
        return URL(url)
            .openConnection()
            .let {
                it as HttpURLConnection
            }.apply {
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                requestMethod = "POST"

                doOutput = true
                val outputWriter = OutputStreamWriter(outputStream)
                outputWriter.write(body)
                outputWriter.flush()
            }.let {
                if (it.responseCode == 200) it.inputStream else it.errorStream
            }.let { streamToRead ->
                BufferedReader(InputStreamReader(streamToRead)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    response.toString()
                }
            }
    }



































}
