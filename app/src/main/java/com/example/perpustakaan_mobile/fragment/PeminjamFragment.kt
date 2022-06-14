package com.example.perpustakaan_mobile.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.perpustakaan_mobile.R
import com.example.perpustakaan_mobile.activity.AddPeminjamActivity
import com.example.perpustakaan_mobile.adapter.PeminjamAdapter
import com.example.perpustakaan_mobile.config.Global
import kotlinx.android.synthetic.main.fragment_peminjam.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PeminjamFragment : Fragment() {
    lateinit var adapterPeminjam: PeminjamAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for activity fragment
        return inflater.inflate(R.layout.fragment_peminjam, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        btnAddPeminjam.setOnClickListener {
            val intent = Intent(activity, AddPeminjamActivity::class.java)
//            intent.putExtra("send_add", SEND_ADD_SHOES)
            startActivity(intent)
        }

        lvPeminjam.setOnItemClickListener { adapterView, view, i, l ->
            var menuPopup = PopupMenu(activity,view)
            menuPopup.menuInflater.inflate(R.menu.menu_popup,menuPopup.menu)
            menuPopup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.menuUpdate -> {
                        var intent = Intent(activity, AddPeminjamActivity::class.java)
//                        intent.putExtra(UPDATE_AKUN,listPeminjam[i])
                        activity?.startActivity(intent)
                        true
                    }
                    R.id.menuDelete -> {
//                        Toast.makeText(activity,"Hapus "+listPeminjam[i].nama, Toast.LENGTH_LONG)
//                        listPeminjam.removeAt(i)
                        adapterPeminjam.notifyDataSetChanged()
                        true
                    }
                }
                false
            }
            menuPopup.show()
        }
    }

    override fun onResume() {
        super.onResume()
        showData("")
    }

    fun showData(namaMhs :String){
        val request = object : StringRequest(
            Request.Method.POST,Global.url+"Peminjaman",
            Response.Listener { response ->
                daftarMhs.clear()
                val jsonArray = JSONArray(response)
                for (x in 0..(jsonArray.length()-1)){
                    val jsonObject = jsonArray.getJSONObject(x)
                    var  mhs = HashMap<String,String>()
                    mhs.put("nim",jsonObject.getString("nim"))
                    mhs.put("nama",jsonObject.getString("nama"))
                    mhs.put("nama_prodi",jsonObject.getString("nama_prodi"))
                    mhs.put("alamat",jsonObject.getString("alamat"))
                    mhs.put("tanggal_lahir",jsonObject.getString("tanggal_lahir"))
                    mhs.put("jenis_kelamin",jsonObject.getString("jenis_kelamin"))
                    mhs.put("url",jsonObject.getString("url"))
                    daftarMhs.add(mhs)
                }
                adapterPeminjam.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Terjadi kesalahan koneksi ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String,String>()
                hm.put("nama",namaMhs)
                return hm
            }
        }
        val queue = Volley.newRequestQueue(activity)
        queue.add(request)
    }

    fun queryInsertUpdateDelete(mode : String){
        val request = object : StringRequest(
            Method.POST,url3,
            Response.Listener{ response ->
                val jsonObject = JSONObject(response)
                val error = jsonObject.getString("kode")
                if(error.equals("000")){
                    Toast.makeText(activity,"Operasi Berhasil",Toast.LENGTH_LONG).show()

                    showDataMhs("")
                }else{
                    Toast.makeText(activity,"Operasi Gagal",Toast.LENGTH_LONG).show()
                }


            },
            Response.ErrorListener { error ->

                Toast.makeText(activity,"Tidak dapat terhubung ke server CRUD",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String,String>()
                val nmFile ="DC"+ SimpleDateFormat("yyyymmdd", Locale.getDefault()).format(Date())+".jpg"
                when(mode){
                    "insert"->{
                        hm.put("mode","insert")
                        hm.put("nim",edNim.text.toString())
                        hm.put("nama",edNama.text.toString())
                        hm.put("image",imStr)
                        hm.put("file",nmFile)
                        hm.put("nama_prodi",pilihprodi)
                        hm.put("alamat",edAlamat.text.toString())
                        hm.put("tanggal_lahir",edTanggal.text.toString())
                        hm.put("jenis_kelamin",edJk.text.toString())

                    }
                    "update"->{
                        hm.put("mode","update")
                        hm.put("nim",edNim.text.toString())
                        hm.put("nama",edNama.text.toString())
                        hm.put("image",imStr)
                        hm.put("file",nmFile)
                        hm.put("nama_prodi",pilihprodi)
                        hm.put("alamat",edAlamat.text.toString())
                        hm.put("tanggal_lahir",edTanggal.text.toString())
                        hm.put("jenis_kelamin",edJk.text.toString())
                    }
                    "delete"->{
                        hm.put("mode","delete")
                        hm.put("nim",edNim.text.toString())


                    }
                }
                return hm
            }
        }
        val queue = Volley.newRequestQueue(activity)
        queue.add(request)

    }
}
