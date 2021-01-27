package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.adapter.ContactsAdapter
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.adapter.OnContactClickListener
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.controller.ContactsController
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Auth
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Contact
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view.MainActivity.Extras.CONTACT
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view.MainActivity.Extras.VIEW_CONTACT

class MainActivity : AppCompatActivity(), OnContactClickListener {
    private lateinit var activivityMainBinding: ActivityMainBinding

    private lateinit var contacts: MutableList<Contact>
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contactsLayoutManager: LinearLayoutManager
    private lateinit var contactsController: ContactsController

    private val newContactRequestCode = 0
    private val editContactRequestCode = 1

    object Extras {
        val CONTACT = "ContactExtra"
        val VIEW_CONTACT = "ViewContact"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activivityMainBinding.root)

        contactsController = ContactsController(this)
        contacts = mutableListOf()

        val getContacts = object : AsyncTask<Void, Void, List<Contact>>() {
            override fun doInBackground(vararg p0: Void?): List<Contact> {
                Thread.sleep(5000)
                return contactsController.getAll()
            }

            override fun onPreExecute() {
                super.onPreExecute()
                activivityMainBinding.progressBar.visibility = View.VISIBLE
            }

            override fun onPostExecute(result: List<Contact>?) {
                super.onPostExecute(result)

                if (result != null) {
                    activivityMainBinding.progressBar.visibility = View.GONE
                    contacts.clear()
                    contacts.addAll(result)
                    contactsAdapter.notifyDataSetChanged()
                }
            }
        }
        getContacts.execute()

        contactsAdapter = ContactsAdapter(contacts, this)
        contactsLayoutManager = LinearLayoutManager(this)

        activivityMainBinding.contactsListRv.adapter = contactsAdapter
        activivityMainBinding.contactsListRv.layoutManager = contactsLayoutManager

    }

    override fun onStart() {
        super.onStart()
        val email = Auth.firebaseAuth.currentUser
        if (email != null) {

        } else {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newContactRequestCode && resultCode == RESULT_OK && data != null) {
            val newContact = data.getParcelableExtra<Contact>(CONTACT)
            if (newContact != null) {
                contactsController.add(newContact)
                contacts.add(newContact)
                contactsAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == editContactRequestCode && resultCode == RESULT_OK && data != null) {
            val contact: Contact? = data.getParcelableExtra<Contact>(CONTACT)
            if (contact != null) {
                contactsController.update(contact)
                contacts[contacts.indexOfFirst { it.name == contact.name }] = contact
                contactsAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onContactClick(position: Int) {
        val contact: Contact = contacts[position]

        val viewIntent = Intent(this, ContactActivity::class.java)
        viewIntent.putExtra(CONTACT, contact)
        viewIntent.action = VIEW_CONTACT

        startActivity(viewIntent)
    }

    override fun onEditMenuItemClick(position: Int) {
        val contact: Contact = contacts[position]

        val editIntent = Intent(this, ContactActivity::class.java)
        editIntent.putExtra(CONTACT, contact)
        startActivityForResult(editIntent, editContactRequestCode)
    }

    override fun onDeleteMenuItemClick(position: Int) {
        if (position != -1) {
            val contact: Contact = contacts[position]

            contactsController.delete(contact.name)
            contacts.removeAt(position)
            contactsAdapter.notifyDataSetChanged()
        }
    }

    fun addContact(view: View) {
        if (view == activivityMainBinding.addContactFab) {
            val newContactIntent = Intent(this, ContactActivity::class.java)
            startActivityForResult(newContactIntent, newContactRequestCode)
        }
    }
}