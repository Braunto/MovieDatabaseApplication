package es.usj.session3.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import es.usj.session3.exercise.Dates.*
import kotlinx.android.synthetic.main.activity_actors_view.*
import kotlinx.android.synthetic.main.activity_movie_list.*

class ActorsView : AppCompatActivity() {


    lateinit var adapter : CustomArrayAdapterActor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors_view)


        val gson= Gson()
        val  mov= gson.fromJson<movie>(intent.getStringExtra("actors"), movie::class.java)


        val lindex= mov.actors.size






        val allactors:String=FicheroActors.get();

        val actorsArray: Array<Actor> = gson.fromJson(
            allactors,
            Array<Actor>::class.java
        )

        val datos: MutableList<Actor>;
        datos =actorsArray.toMutableList();

        val lindexActor= datos.size


        val actorFilms = mutableListOf<Actor>()


        for (i in 0..lindex-1) {


            for (x in 0..lindexActor-1){

                if(mov.actors[i]==datos.get(x).id)
                {

                    actorFilms.add(datos.get(x))
                    break

                }


            }

        }




        setContentView(R.layout.activity_actors_view)
        adapter = CustomArrayAdapterActor(context = this, resourceId = R.layout.row_element, items = actorFilms)
        lvListActors.adapter = this.adapter




        lvListActors.setOnItemClickListener { parent, view, position, _ ->
            val intent = Intent(this, ActorParticipation::class.java)
            val Actor = actorFilms[position]
            intent.putExtra("actor", gson.toJson(Actor))
            startActivity(intent)
            adapter.getView(position, view, parent)
        }

        //tvActors.setText(mov.actors[3].toString())
       // tvActors.setText(lindexActor.toString())
       // actorFilms.add(datos.get(0))
       // tvActors.setText(actorFilms.get(0).name)






    }
}
