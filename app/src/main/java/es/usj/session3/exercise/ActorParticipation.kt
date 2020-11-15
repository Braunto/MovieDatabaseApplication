package es.usj.session3.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import es.usj.session3.exercise.Dates.*
import kotlinx.android.synthetic.main.activity_actor_participation.*
import kotlinx.android.synthetic.main.activity_movie_list.*

class ActorParticipation : AppCompatActivity() {


    lateinit var adapter : CustomArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_participation)



        val gson= Gson()


        val  actor1= gson.fromJson<Actor>(intent.getStringExtra("actor"), Actor::class.java)


        val actorWithFilms = mutableListOf<movie>()


        val allmovies:String= FIcheroPeliculas.get();

        val movieArray: Array<movie> = gson.fromJson(
            allmovies,
            Array<movie>::class.java
        )

        val datos: MutableList<movie>;
        datos =movieArray.toMutableList();




        for(i in 0..datos.size-1) {


            for (x in 0..(datos.get(i).actors.size - 1)) {
                if (actor1.id == datos.get(i).actors[x]) {
                    actorWithFilms.add(datos.get(i));
                }

            }
        }






        setContentView(R.layout.activity_actor_participation)
        adapter = CustomArrayAdapter(context = this, resourceId = R.layout.row_element, items = actorWithFilms)
        lvFinalActors.adapter = this.adapter







        tvFinalActor.setText(actor1.name)


    }
}
