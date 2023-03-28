package com.harjot.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.harjot.chatapp.databinding.ActivityLoginBinding
import java.security.AuthProvider

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            if(binding.etEmail.text.toString().trim().isNullOrEmpty()){
                binding.etEmail.error = "Enter Email"
            }
            else if(binding.etPassword.text.toString().trim().isNullOrEmpty()){
                binding.etPassword.error = "Enter Password"
            }
            else{
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()

                login(email,password)
            }
        }
        binding.btnRegister.setOnClickListener {
            var intent = Intent(this@Login,Register::class.java)
            startActivity(intent)
        }
    }
    fun login(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful){
                    var intent = Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@Login, "User Doesn't Exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}