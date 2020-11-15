package es.usj.session3.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import es.usj.session3.exercise.Dates.*
import kotlinx.android.synthetic.main.activity_actor_participation.*
import kotlinx.android.synthetic.main.activity_genre_participation.*

class GenreParticipation : AppCompatActivity() {

    lateinit var adapter : CustomArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_participation)


        val gson= Gson()


        val  genre1 = gson.fromJson<Genre>(intent.getStringExtra("gen"), Genre::class.java)


        val GenreWithFilms = mutableListOf<movie>()


        val allmovies:String= FIcheroPeliculas.get();

        val movieArray: Array<movie> = gson.fromJson(
            allmovies,
            Array<movie>::class.java
        )

        val datos: MutableList<movie>;
        datos =movieArray.toMutableList();




        for(i in 0..datos.size-1) {


            for (x in 0..(datos.get(i).genres.size - 1)) {
                if (genre1.id == datos.get(i).genres[x]) {
                    GenreWithFilms.add(datos.get(i));
                }

            }
        }






        setContentView(R.layout.activity_genre_participation)
        adapter = CustomArrayAdapter(context = this, resourceId = R.layout.row_element, items = GenreWithFilms)
        lvFinalGenres.adapter = this.adapter







        tvFinalGenre.setText(genre1.name)


    }
}
