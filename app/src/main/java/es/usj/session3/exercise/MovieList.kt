package es.usj.session3.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import es.usj.session3.exercise.Dates.CustomArrayAdapter
import es.usj.session3.exercise.Dates.FIcheroPeliculas
import es.usj.session3.exercise.Dates.movie
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieList : AppCompatActivity() {


    lateinit var adapter : CustomArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)


        val allmovies:String=FIcheroPeliculas.get();


        val gson= Gson()
        val movieArray: Array<movie> = gson.fromJson(
            allmovies,
            Array<movie>::class.java
        )

        val datos: MutableList<movie>;
        datos =movieArray.toMutableList();


        setContentView(R.layout.activity_movie_list)
        adapter = CustomArrayAdapter(context = this, resourceId = R.layout.row_element, items = datos)
        lvListisima.adapter = this.adapter



        lvListisima.setOnItemClickListener { parent, view, position, _ ->
            val intent = Intent(this, ViewMovie::class.java)
            val movie = datos[position]
            intent.putExtra("movie", gson.toJson(movie))
            startActivity(intent)
            adapter.getView(position, view, parent)
        }





        btnGoContact.setOnClickListener(){
            val intent = Intent(this, Contact::class.java)
            startActivity(intent)
        }



        btnAdd.setOnClickListener() {

            val intent = Intent(this, AddMovie::class.java)
            startActivity(intent)
        }


        lvListisima.setOnItemLongClickListener{ parent, view, position, _ ->
            val intent = Intent(this, EditMovie::class.java)
            val movie = datos[position]
            intent.putExtra("edited", gson.toJson(movie))
            startActivity(intent)
            adapter.getView(position, view, parent)
            return@setOnItemLongClickListener true
        }








    }
}
