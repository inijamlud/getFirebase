package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapplication.FirebaseAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var fire: DatabaseReference
    private lateinit var layoutku: RecyclerView
    private var ambilDatabase: ArrayList<FirebaseDataClassView> = arrayListOf()
    lateinit var btnAdd: View
    lateinit var btnSignOut: View
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutku = findViewById(R.id.rv_data)
        btnAdd = findViewById(R.id.btn_main_add)
        btnSignOut = findViewById(R.id.btn_main_signout)
        firebaseAuth = FirebaseAuth.getInstance()

        fire = FirebaseDatabase.getInstance().getReference("employee")
        btnAdd.setOnClickListener{
            startActivity(Intent(this, AddActivity::class.java))
        }

        btnSignOut.setOnClickListener{
            firebaseAuth.signOut()
            checkme()
        }
    }

    override fun onStart(){
        super.onStart()
        loadMeFirst()
        checkme()
    }

    fun checkme(){
        val currentUser = firebaseAuth!!.currentUser
        if (currentUser == null){
            val pindah = Intent(this, LoginActivity::class.java)
            startActivity(pindah)
        }
    }

    private fun loadMeNow(){
        layoutku.layoutManager = LinearLayoutManager(this)
        val myA = FirebaseAdapter(ambilDatabase)
        layoutku.adapter = myA
    }

    private fun loadMeFirst(){
        fire.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    ambilDatabase.clear()
                    for (x in snapshot.children){
                        val mymy = x.getValue(FirebaseDataClassView::class.java)
                        mymy!!.uid = x.key.toString()
                        ambilDatabase.add(mymy!!)
                    }
                }
                loadMeNow()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}