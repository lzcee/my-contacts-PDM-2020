package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

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

        contacts = contactsController.getAll()

        contactsAdapter = ContactsAdapter(contacts)
        contactsLayoutManager = LinearLayoutManager(this)

        activivityMainBinding.contactsListRv.adapter = contactsAdapter
        activivityMainBinding.contactsListRv.layoutManager = contactsLayoutManager

    }

    override fun onStart() {
        super.onStart()
        val email = Auth.firebaseAuth.currentUser
        if (email != null) {
            val contactsDAO = ContactsDAO();
            contactsDAO.create(Contact("teste","988433255","teste@teste.com"))
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