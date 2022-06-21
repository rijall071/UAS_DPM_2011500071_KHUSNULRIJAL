package com.example.uas_dpm_2011500071

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var adpDosen: Adapter_dosen
    private lateinit var Data_campuss: ArrayList<Data_campuss>
    private lateinit var lvKampus: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvKampus = findViewById(R.id.lvKampus)
        linTidakAda = findViewById(R.id.linTidakAda)

        Data_campuss = ArrayList()
        adpDosen = Adapter_dosen(this@MainActivity, Data_campuss)

        lvKampus.adapter =adpDosen

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, entri_dosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db = campuss(this@MainActivity)
        val data = db.tampil()
        repeat(Data_campuss.size) { Data_campuss.removeFirst()}
        if(data.count > 0 ){
            while(data.moveToNext()){
                val matkul = Data_campuss(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpDosen.add(matkul)
                adpDosen.notifyDataSetChanged()
            }
            lvKampus.visibility = View.VISIBLE
            linTidakAda.visibility  = View.GONE
        } else {
            lvKampus.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}



