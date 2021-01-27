package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.adapter

interface OnContactClickListener {
    fun onContactClick(position: Int)

    fun onEditMenuItemClick(position: Int)
    fun onDeleteMenuItemClick(position: Int)
}