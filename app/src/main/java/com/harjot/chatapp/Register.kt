package com.harjot.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.harjot.chatapp.databinding.ActivityLoginBinding
import com.harjot.chatapp.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        binding.btnSubmit.setOnClickListener {
            if(binding.etFirstName.text.toString().trim().isNullOrEmpty()){
                binding.etFirstName.error = "Enter Name"
            }
            else if(binding.etEnterEmail.text.toString().trim().isNullOrEmpty()){
                binding.etEnterEmail.error = "Enter Email"
            }
            else if(binding.etSetPassword.text.toString().trim().isNullOrEmpty()){
                binding.etSetPassword.error = "Enter Password"
            }
            else{
                val name = binding.etFirstName.text.toString()
                val email = binding.etEnterEmail.text.toString()
                val password = binding.etSetPassword.text.toString()

                signup(name,email,password)
            }
        }
    }
    fun signup(name:String, email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {task->
                if (task.isSuccessful){
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    var intent = Intent(this@Register,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@Register, "Some Error Occured", Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun addUserToDatabase(name:String, email: String, uid:String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(UserModel(name, email, uid))
    }
}