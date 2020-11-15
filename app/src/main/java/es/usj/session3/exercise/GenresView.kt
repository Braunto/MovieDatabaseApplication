package es.usj.session3.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import es.usj.session3.exercise.Dates.*
import kotlinx.android.synthetic.main.activity_actors_view.*
import kotlinx.android.synthetic.main.activity_genres_view.*

class GenresView : AppCompatActivity() {

    lateinit var adapter : CustomArrayAdapterGenres

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres_view)



        val gson= Gson()
        val  mov= gson.fromJson<movie>(intent.getStringExtra("genres"), movie::class.java)


        val lindex= mov.genres.size






        val allGenres:String= FicheroGenres.get();

        val GenresArray: Array<Genre> = gson.fromJson(
            allGenres,
            Array<Genre>::class.java
        )

        val datos: MutableList<Genre>;
        datos =GenresArray.toMutableList();

        val lindexGenre= datos.size


        val genreFilms = mutableListOf<Genre>()


        for (i in 0..lindex-1) {


            for (x in 0..lindexGenre-1){

                if(mov.genres[i]==datos.get(x).id)
                {

                    genreFilms.add(datos.get(x))
                    break

                }


            }

        }




        setContentView(R.layout.activity_genres_view)
        adapter = CustomArrayAdapterGenres(context = this, resourceId = R.layout.row_element, items = genreFilms)
        ListGenres.adapter = this.adapter




        ListGenres.setOnItemClickListener { parent, view, position, _ ->
            val intent = Intent(this, GenreParticipation::class.java)
            val Genre = genreFilms[position]
            intent.putExtra("gen", gson.toJson(Genre))
            startActivity(intent)
            adapter.getView(position, view, parent)
        }

        //tvActors.setText(mov.actors[3].toString())
        // tvActors.setText(lindexActor.toString())
        // actorFilms.add(datos.get(0))
        // tvActors.setText(actorFilms.get(0).name)






    }
}
