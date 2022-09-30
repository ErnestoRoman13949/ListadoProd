package com.earl.listadoprod

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.earl.listadoprod.databinding.ActivityMainBinding
import dataadapter.ProductoAdapter
import dataclass.Producto
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()

    private var theID = ""
    private var editar = false
    private var editado: Producto = Producto(-1, "", 0.00);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    private fun limpiar(){
        with(binding){
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd(){
        with(binding){
            try{
                val id: Int = etID.text.toString().toInt();
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            }catch(ex: Exception){
                Toast.makeText(this@MainActivity, "Error: ${ex.toString()}",
                Toast.LENGTH_LONG).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                {producto -> selectItem(producto)},
                {producto -> validarDelete(producto)})

            limpiar()
            etID.setText(theID.toString())
        }
    }

    private fun iniciar(){
        binding.btnAgregar.setOnClickListener{
            if(!editar)
            agregarProd()
            else
                finishEdit()
        }
        binding.btnLimpiar.setOnClickListener{
            limpiar()
        }
    }

    private fun eliminarProd(producto: Producto){
        try {
            listaProd.remove(producto)
            binding.rcvLista.adapter = ProductoAdapter(listaProd,
                {producto -> selectItem(producto)},
                {producto -> validarDelete(producto)})

        }catch (ex: Exception){
            Toast.makeText(this@MainActivity, "ERROR ${ex.toString()}", Toast.LENGTH_LONG).show()
        }
    }

    private fun validarDelete(producto: Producto){
        var aviso = AlertDialog.Builder(this)
        aviso.setMessage("¿Desea eliminar el Producto?")
            .setCancelable(false)
            .setPositiveButton("SI", DialogInterface.OnClickListener{
                dialog, id -> eliminarProd(producto)
            })
            .setNegativeButton("NO", DialogInterface.OnClickListener{
                dialog, id -> })

        val msg = aviso.create()
        aviso.setTitle("Eliminar Producto")
        aviso.show()
    }

    private fun selectItem(producto: Producto){
        try {
            with(binding){
                etID.setText(producto.id.toString())
                etNombreProd.setText(producto.nombre.toString())
                etPrecio.setText(producto.precio.toString())
                btnAgregar.setText("Aplicar")
                editar = true
                editado = producto
            }
        }catch (ex: Exception){
            Toast.makeText(this@MainActivity, "ERROR ${ex.toString()}", Toast.LENGTH_LONG).show()
        }
    }

    private fun finishEdit(){
        try {
            with(binding){
                var select = listaProd.indexOf(editado)
                editado = Producto(editado.id, etNombreProd.text.toString(),
                    etPrecio.text.toString().toDouble())
                listaProd[select] = editado
                rcvLista.adapter = ProductoAdapter(listaProd,
                    {producto -> selectItem(producto)},
                    {producto -> validarDelete(producto)})
                etID.setText(theID.toString())
                btnAgregar.setText("Agregar")
                etID.setText("")
                etNombreProd.setText("")
                etPrecio.setText("")
                etID.requestFocus()
            }
            editar = false
            editado = Producto(-1, "", 0.00)
        }catch (ex: Exception){
            Toast.makeText(this@MainActivity, "ERROR ${ex.toString()}", Toast.LENGTH_LONG).show()
        }
    }
}