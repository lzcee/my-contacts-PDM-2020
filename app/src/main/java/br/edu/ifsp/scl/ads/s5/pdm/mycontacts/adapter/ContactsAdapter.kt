package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.R
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Contact

class ContactsAdapter(
    private val contactsList: MutableList<Contact>,
    private val onContactClickListener: OnContactClickListener
) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView),
        View.OnCreateContextMenuListener {
        val contactNameTv = itemView.findViewById<TextView>(R.id.contactNameTv)
        val contactPhoneTv = itemView.findViewById<TextView>(R.id.contactPhoneTv)
        val contactEmailTv = itemView.findViewById<TextView>(R.id.contactEmailTv)

        init {
            listItemView.setOnCreateContextMenuListener(this)
        }

        private val invalidPosition = -1
        var positionClick: Int = invalidPosition

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add("Editar Contato")?.setOnMenuItemClickListener {
                if (positionClick != invalidPosition) {
                    onContactClickListener.onEditMenuItemClick(positionClick)
                    true
                } else {
                    false
                }
            }
            menu?.add("Remover Contato")?.setOnMenuItemClickListener {
                if (positionClick != invalidPosition) {
                    onContactClickListener.onDeleteMenuItemClick(positionClick)
                    true
                } else {
                    false
                }
            }
        }

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

        viewHolder.itemView.setOnClickListener {
            onContactClickListener.onContactClick(position)
        }

        viewHolder.positionClick = position
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }
}

private fun View.OnCreateContextMenuListener.onEditMenuItemClick(positionClick: Int) {

}
