package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.databinding.ActivityContactBinding
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Contact

class ContactActivity : AppCompatActivity() {
    private lateinit var activityContactBinding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContactBinding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(activityContactBinding.root)
    }

    fun addContact(view: View) {
        val newContact = Contact(
            activityContactBinding.contactNameEt.text.toString(),
            activityContactBinding.contactPhoneEt.text.toString(),
            activityContactBinding.contactEmailEt.text.toString()
        )

        val intent = Intent()
        intent.putExtra(MainActivity.Extras.contact, newContact)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}