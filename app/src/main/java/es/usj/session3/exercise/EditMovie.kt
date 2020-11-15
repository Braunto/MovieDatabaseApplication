package es.usj.session3.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.Gson
import es.usj.session3.exercise.Dates.*
import kotlinx.android.synthetic.main.activity_edit_movie.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class EditMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)


        val gson = Gson()


        val mov = gson.fromJson<movie>(intent.getStringExtra("edited"), movie::class.java)


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



        if (spinnerActors != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, ArrayNamesActors
            )
            spinnerActors.adapter = adapter
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



        if (spinnerGenres != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, ArrayNamesGenres
            )
            spinnerGenres.adapter = adapter
        }



















        etFilm.setText(mov.title)
        etDirector.setText(mov.director)
        etRating.setText(mov.rating.toString())
        etRuntime.setText(mov.runtime.toString())
        etYear.setText(mov.year.toString())
        etDescription.setText(mov.description)
        etVotes.setText(mov.votes.toString())
        etRevenue.setText(mov.revenue.toString())



        btnEdit.setOnClickListener() {



            var idActor = mov.actors[0]
            var idGenres = mov.genres[0]


            //Meter si no es seleccionado

            if (spinnerActors.getSelectedItem() != "") {


                for (i in 0..ArrayNamesActors.size - 1) {


                    if (spinnerActors.getSelectedItem() == datos.get(i).name) {

                        idActor = datos.get(i).id
                        break

                    }


                }


            }



                if (spinnerGenres.getSelectedItem() != "") {



                    for (i in 0..ArrayNamesGenres.size - 1) {


                        if (spinnerGenres.getSelectedItem() == datosGenres.get(i).name) {

                            idGenres = datosGenres.get(i).id
                            break

                        }


                    }



                }


            var      movieEdited = """{
                                        "id": """" + mov.id + """",
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
                    post("$UpdateServer", movieEdited);
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

