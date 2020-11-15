package es.usj.session3.exercise.Dates

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.row_element.view.*

class CustomArrayAdapterGenres : ArrayAdapter<Genre> {


    val resourceId: Int

    class ViewHolder {
        lateinit var text1 : TextView
        lateinit var text2 : TextView
    }


    constructor(context: Context, resourceId: Int, items: MutableList<Genre>) : super(context, resourceId, items) {
        this.resourceId = resourceId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val timestamp = System.currentTimeMillis()
        if(view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(resourceId, null)
            Log.d("ADAPTER NEW VIEW", "New view ${System.currentTimeMillis() - timestamp}")
            val viewHolder =
                ViewHolder()
            viewHolder.text1 = view.text1 as TextView
            viewHolder.text2 = view.text2 as TextView
            view.tag = viewHolder
        } else {
            Log.d("ADAPTER REUSED VIEW", "Reused view ${System.currentTimeMillis() - timestamp}")
        }
        val value = getItem(position)
        val holder = view!!.tag as ViewHolder
        holder.text1.text = "Id: ${value!!.id}"
        holder.text2.text = "Name: ${value!!.name}"
        //Log.d("ADAPTER GET VIEW", value!!.)
        return view
    }
}