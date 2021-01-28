package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.dao

import android.util.Log
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Auth
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Contact
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ContactsDAO {
    private val userId = Auth.firebaseAuth.currentUser?.uid
    private val database = Firebase.database.getReference("users/$userId")
    private val contacts: MutableList<Contact> = mutableListOf()

    fun create(contact: Contact) {
        if (userId != null) {
            database.child(contact.name).setValue(contact)
        }
    }

    fun update(contact: Contact) {
        if (userId != null) {
            database.child(contact.name).setValue(contact)
        }
    }

    fun delete(name: String) {
        if (userId != null) {
            database.child(name).removeValue()
        }
    }

    fun getOne(name: String): Contact {
        if (userId != null) {
            return contacts[contacts.indexOfFirst { it.name == name }]
        }
        return Contact()
    }

    fun getAll(): MutableList<Contact> = contacts

    private val childEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val contact: Contact = snapshot.getValue<Contact>() ?: Contact()

            if (contacts.indexOfFirst { it.name == contact.name } == -1) {
                contacts.add(contact)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("onCancelled", error.message)
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val contact: Contact = snapshot.getValue<Contact>() ?: Contact()

            contacts[contacts.indexOfFirst { it.name == contact.name }] = contact
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val contact: Contact = snapshot.getValue<Contact>() ?: Contact()

            contacts.remove(contact)
        }

    }

    init {
        database.addChildEventListener(childEventListener)
    }
}