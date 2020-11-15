package es.usj.session3.exercise

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contact.*

class Contact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)





        tvDescriptionContact.setText("About me:\n I am an university student that want to finish with covid\n to be able to go Japan")

        btnCall.setOnClickListener {


            val telephone="666666666"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +telephone))
            startActivity(intent)


        }



        btnEmail.setOnClickListener(){



            val email="Kobito@gmail.com"
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, email)
            startActivity(intent)

        }


        btnUrl.setOnClickListener(){


            val UrlContact="https://www.usj.es/"
            val intent3= Intent(Intent.ACTION_VIEW)
            intent3.data = Uri.parse(UrlContact)
            startActivity(intent3)



        }



    }
}
