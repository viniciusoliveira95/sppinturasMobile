package br.com.sppinturasapp

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.orcamento_adapter.view.*

class PedidoAdapter (val pedidos: List<Pedido>, val onClick: (Pedido) -> Unit):
            RecyclerView.Adapter<PedidoAdapter.PedidosViewHolder>() {

        // ViewHolder com os elementos da tela
        class PedidosViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val cardNome: TextView
            var cardView: CardView
            var cardDtPedido: TextView

            init {
                cardNome = view.findViewById<TextView>(R.id.cardNomeSolicitante)
                cardView = view.findViewById<CardView>(R.id.card_pedidos)
                cardDtPedido = view.findViewById<TextView>(R.id.cardDtPedido)
            }
        }

        // Quantidade de orcamentos na lista
        override fun getItemCount() = this.pedidos.size
        // inflar layout do adapter

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosViewHolder {
            // infla view no adapter
            val view = LayoutInflater.from(parent.context).inflate(R.layout.pedido_adapter, parent, false)

            // retornar ViewHolder
            val holder = PedidosViewHolder(view)
            return holder
        }

        override fun onBindViewHolder(holder: PedidosViewHolder, position: Int) {
            val context = holder.itemView.context

            // recuperar objeto orcamento
            val pedido = pedidos[position]

            // atualizar dados de orcamento
            holder.cardNome.text = pedido.nomeSolicitante
            holder.cardDtPedido.text = context.resources.getString(R.string.cardDataPedido, pedido.dtPedido)

            // adiciona evento de clique
            holder.itemView.setOnClickListener {onClick(pedido)}
    }
}