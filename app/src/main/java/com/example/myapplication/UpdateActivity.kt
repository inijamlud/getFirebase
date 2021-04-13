package com.example.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    lateinit var btnSubmit:Button
    lateinit var btnCancel:Button
    lateinit var etName: EditText
    lateinit var etPrice: EditText
    lateinit var valName: String
    lateinit var valPrice: String
    lateinit var valUid: String
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        getMyData()
        declaration()
        myfunction()
    }

    fun declaration(){
        btnSubmit = findViewById(R.id.btn_up_submit)
        btnCancel = findViewById(R.id.btn_up_cancel)
        etPrice = findViewById(R.id.et_up_price)
        etName = findViewById(R.id.et_up_name)
        ref = FirebaseDatabase.getInstance().getReference("employee").child(valUid)
    }


    fun getMyData(){
        val myValue = intent.extras
        if (myValue!=null){
            valName = myValue.getString("fullname").toString()
            valPrice = myValue.getString("email").toString()
            valUid = myValue.getString("uid").toString()
        }
    }

    fun myfunction(){
        etName.setText(valName)
        etPrice.setText(valPrice)
        val pindah = Intent(this, MainActivity::class.java)
        btnSubmit.setOnClickListener {
            ref.child("fullname").setValue(etName.text.toString())
            ref.child("email").setValue(etPrice.text.toString())
            startActivity(pindah)
        }
        btnCancel.setOnClickListener {
            startActivity(pindah)
        }
    }
}