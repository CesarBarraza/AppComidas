package com.example.appinfinixsoft.UI

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appinfinixsoft.data.local.SharedPreferencesManager
import com.example.appinfinixsoft.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = binding.toolbarLogin.toolbar
        setSupportActionBar(toolbar).apply {
            title = "Login"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        /*Login de usuario*/
        binding.btnLogin.setOnClickListener {
            val usuario = SharedPreferencesManager.getUser(this)
            if(usuario?.username == binding.txtNombreUsuario.text.toString()
                && usuario?.password == binding.txtPassword.text.toString()){
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }else{
                mostrarMensaje()
            }
        }
    }

    /*función modal de datos login incorrectos*/
    private fun mostrarMensaje(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("MENSAJE")
        builder.setMessage("Usuario o password incorrectos")
        val dialog = builder.create()
        dialog.show()
    }

    /*Función para la flecha de volver en el toolbar*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}