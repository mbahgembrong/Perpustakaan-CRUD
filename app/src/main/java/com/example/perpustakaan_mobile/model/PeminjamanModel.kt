package com.example.perpustakaan_mobile.model

data class PeminjamanModel (
    var id:String,
    var peminjam:PeminjamModel,
    var buku:BukuModel,
    var tanggalPeminjaman:String,
    var tanggalPengembalian:String,
    var waktuPengembalian:String
)
