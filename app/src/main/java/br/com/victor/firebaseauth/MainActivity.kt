package br.com.victor.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.victor.firebaseauth.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var btnDeslogar : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnDeslogar = findViewById(R.id.btnDeslogar)
        btnDeslogar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intentCadastro = Intent(this, CreateAccountActivity::class.java)
            startActivity(intentCadastro)
            finish()
        }


    }



}
