package com.example.perpustakaan_mobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.perpustakaan_mobile.R
import com.example.perpustakaan_mobile.fragment.BukuFragment
import com.example.perpustakaan_mobile.fragment.PeminjamFragment
import com.example.perpustakaan_mobile.fragment.PeminjamanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavListener)
        var fr = supportFragmentManager.beginTransaction()
        fr.add(R.id.fragmentContainerView, BukuFragment())
        fr.commit()
    }
    private val onBottomNavListener= BottomNavigationView.OnNavigationItemSelectedListener { i->
        when(i.itemId){
            R.id.menuBuku ->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, BukuFragment())
                    commit()
                }
            }
            R.id.menuPeminjam ->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, PeminjamFragment())
                    commit()
                }
            } R.id.menuPeminjaman ->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, PeminjamanFragment())
                    commit()
                }
            }
        }
        true
    }
}
