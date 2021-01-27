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

        val contact: Contact? = intent.getParcelableExtra(MainActivity.Extras.CONTACT)
        if (contact != null) {
            activityContactBinding.contactNameEt.setText(contact.name)
            activityContactBinding.contactNameEt.isEnabled = false
            activityContactBinding.contactPhoneEt.setText(contact.phone)
            activityContactBinding.contactEmailEt.setText(contact.email)


            if (intent.action == MainActivity.Extras.VIEW_CONTACT) {
                activityContactBinding.contactViewTv.text = "Visualizar Contato"
                activityContactBinding.contactPhoneEt.isEnabled = false
                activityContactBinding.contactEmailEt.isEnabled = false
                activityContactBinding.addContactBt.visibility = View.GONE
            } else {
                activityContactBinding.contactViewTv.text = "Editar Contato"
                activityContactBinding.addContactBt.text = "Editar Contato"
            }
        }
    }

    fun addContact(view: View) {
        val newContact = Contact(
            activityContactBinding.contactNameEt.text.toString(),
            activityContactBinding.contactPhoneEt.text.toString(),
            activityContactBinding.contactEmailEt.text.toString()
        )

        val intent = Intent()
        intent.putExtra(MainActivity.Extras.CONTACT, newContact)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}