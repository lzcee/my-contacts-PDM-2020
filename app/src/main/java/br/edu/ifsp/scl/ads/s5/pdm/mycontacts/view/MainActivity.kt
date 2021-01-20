package br.edu.ifsp.scl.ads.s5.pdm.mycontacts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.s5.pdm.mycontacts.model.Auth

class MainActivity : AppCompatActivity() {
    private lateinit var activivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activivityMainBinding.root)
    }

    override fun onStart() {
        super.onStart()
        val email = Auth.firebaseAuth.currentUser
        if (email != null) {
            //activivityMainBinding.bemVindoTv.text = "Seja bem-vindo $email"
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
}