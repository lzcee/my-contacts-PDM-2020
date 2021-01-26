package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.adapter.ContactsAdapter
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.controller.ContactsController
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.dao.ContactsDAO
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Auth
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Contact

class MainActivity : AppCompatActivity() {
    private lateinit var activivityMainBinding: ActivityMainBinding

    private lateinit var contacts: MutableList<Contact>
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contactsLayoutManager: LinearLayoutManager
    private lateinit var contactsController: ContactsController

    private val newContactActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activivityMainBinding.root)

        contactsController = ContactsController(this)
        contacts =  mutableListOf()

        val getContacts = object: AsyncTask<Void, Void, List<Contact>>() {
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

        contactsAdapter = ContactsAdapter(contacts)
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

    fun onClick(view: View) {
//        if (view == activivityMainBinding.sairBt) {
//            Auth.firebaseAuth.signOut()
//            finish()
//        }
    }

    fun addContact(view: View) {

    }
}