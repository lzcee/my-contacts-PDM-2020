package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val name: String = "",
    var phone: String = "",
    var email: String = ""
): Parcelable