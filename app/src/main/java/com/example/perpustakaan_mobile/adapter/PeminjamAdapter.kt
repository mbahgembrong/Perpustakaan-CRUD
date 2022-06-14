package com.example.perpustakaan_mobile.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.perpustakaan_mobile.R
import com.example.perpustakaan_mobile.model.PeminjamModel
import com.squareup.picasso.Picasso

class PeminjamAdapter(val dataPeminjam: ArrayList<PeminjamModel>, var activity: Activity) : BaseAdapter() {
    private val inflater: LayoutInflater
            = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.fragment_peminjam, parent, false)

        val namaTextView = rowView.findViewById(R.id.txtNamaListPeminjam) as TextView
        val nomorHpTextView = rowView.findViewById(R.id.txtTelpListPeminjam) as TextView
        val alamatTextView = rowView.findViewById(R.id.txtAlamatListPeminjam) as TextView
        val imgPeminjamImageView = rowView.findViewById(R.id.imgListPeminjam) as ImageView
        val peminjam = getItem(position) as PeminjamModel
        namaTextView.text =peminjam.nama
        alamatTextView.text =peminjam.alamat
        nomorHpTextView.text =peminjam.telp
        Picasso.get().load(peminjam.foto).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imgPeminjamImageView)

        notifyDataSetChanged()
        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataPeminjam[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataPeminjam.size
    }
}
