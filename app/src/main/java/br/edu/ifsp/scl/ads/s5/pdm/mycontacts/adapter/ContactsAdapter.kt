package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Contact

class ContactsAdapter(private val contactsList: MutableList<Contact>):
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val contactNameTv = itemView.findViewById<TextView>(R.id.contactNameTv)
        val contactPhoneTv = itemView.findViewById<TextView>(R.id.contactPhoneTv)
        val contactEmailTv = itemView.findViewById<TextView>(R.id.contactEmailTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_contact, parent, false)

        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ContactsAdapter.ViewHolder, position: Int) {
        val contact: Contact = contactsList[position]

        val contactNameTv = viewHolder.contactNameTv
        val contactPhoneTv = viewHolder.contactPhoneTv
        val contactEmailTv = viewHolder.contactEmailTv

        contactNameTv.text = contact.name
        contactPhoneTv.text = contact.phone
        contactEmailTv.text = contact.email
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }
}