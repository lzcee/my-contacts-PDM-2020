package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.controller

import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.dao.ContactsDAO
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Contact
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view.MainActivity

class ContactsController (mainActivity: MainActivity) {
    private val contactsDAO: ContactsDAO = ContactsDAO()

    fun add(contact: Contact) = contactsDAO.create(contact)
    fun update(contact: Contact) = contactsDAO.update(contact)
    fun delete(name: String) = contactsDAO.delete(name)
    fun getOne(name: String) = contactsDAO.getOne(name)
    fun getAll() = contactsDAO.getAll()
}