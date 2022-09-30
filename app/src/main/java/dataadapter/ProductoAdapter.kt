package dataadapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import com.earl.listadoprod.databinding.ItemlistaBinding
import dataclass.Producto

class ProductoAdapter(
    val listProd: List<Producto>,
    private val onClickListener: (Producto) ->Unit,
    private val deleteClickListener: (Producto) -> Unit
    ):RecyclerView.Adapter<ProductoAdapter.ProductoHolder>(){

        inner class ProductoHolder(val binding: ItemlistaBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun cargar(
                producto: Producto, onClickListener: (Producto) -> Unit,
                deleteClickListener: (Producto) -> Unit
                ){
                with(binding){
                    tvCodProd.text = producto.id.toString()
                    tvNombreProd.text = producto.nombre
                    tvPrecioProd.text = producto.precio.toString()
                    btnEditar.setOnClickListener{
                        onClickListener(producto)
                    }
                    btnEliminar.setOnClickListener{
                        deleteClickListener(producto)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position], onClickListener, deleteClickListener)
    }

    override fun getItemCount(): Int = listProd.size
    }