package com.example.appinfinixsoft.UI

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.appinfinixsoft.data.entity.Usuario
import com.example.appinfinixsoft.data.local.SharedPreferencesManager
import com.example.appinfinixsoft.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistroBinding.inflate(LayoutInflater.from(this))
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbarRegistro.toolbar
        setSupportActionBar(toolbar).apply {
            title = "Sing Up"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }

        /*Registro de usuario*/
        binding.btnRegistro.setOnClickListener {
            val usuario = Usuario(
                nombre = binding.txtNombre.text.toString(),
                apellido = binding.txtApellido.text.toString(),
                username = binding.txtNombreUsuario.text.toString(),
                email = binding.txtEmail.text.toString(),
                password = binding.txtPassword.text.toString(),
                confirmarPassword = binding.txtConfirmarPassword.text.toString()

            )
            /*Aqui se validan los datos que el usuario ingresa al registrarse*/
            when{
                valoresVacios() -> mensajeValoresVacios()
                binding.txtPassword.text.toString() != binding.txtConfirmarPassword.text.toString()
                -> passwordDiferente()
                !binding.btnCheck.isChecked -> mensajeCheckTerminosCondiciones()
                else -> {
                    SharedPreferencesManager.saveUser(this, usuario)
                    val intent = Intent(this@RegistroActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
     /*Verifica que todos los campos esten completos en el registro*/
    private fun valoresVacios(): Boolean{
        return binding.txtNombre.text.isNullOrEmpty() &&
                binding.txtApellido.text.isNullOrEmpty() &&
                binding.txtEmail.text.isNullOrEmpty() &&
                binding.txtPassword.text.isNullOrEmpty()
    }

    /*Función para checkear si se aceptarón los terminos y condiciones*/
    private fun mensajeCheckTerminosCondiciones() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ATENCIÓN!!!")
        builder.setMessage("Debe aceptar los Terminos y Condiciones")
        val dialog = builder.create()
        dialog.show()
    }

    /*Mensaje de de atención de campos vacios*/
    private fun mensajeValoresVacios() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ATENCIÓN!!!")
        builder.setMessage("Todos los campos son necesarios para crear un usuario!!!")
        val dialog = builder.create()
        dialog.show()
    }

    /*Mensaje de password diferente a confirmar password*/
    fun passwordDiferente(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ATENCIÓN!!!")
        builder.setMessage("Los passwords ingresados son diferentes!!!")
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

