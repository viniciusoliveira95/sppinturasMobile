package br.com.sppinturasapp

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.orcamento_adapter.view.*

class OrcamentoAdapter (val orcamentos: List<Orcamento>, val onClick: (Orcamento) -> Unit):
            RecyclerView.Adapter<OrcamentoAdapter.OrcamentosViewHolder>() {

        // ViewHolder com os elementos da tela
        class OrcamentosViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val cardNome: TextView
            var cardView: CardView

            init {
                cardNome = view.findViewById<TextView>(R.id.cardNomeOrcamento)
                cardView = view.findViewById<CardView>(R.id.card_orcamentos)
            }
        }

        // Quantidade de orcamentos na lista
        override fun getItemCount() = this.orcamentos.size
        // inflar layout do adapter

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrcamentosViewHolder {
            // infla view no adapter
            val view = LayoutInflater.from(parent.context).inflate(R.layout.orcamento_adapter, parent, false)

            // retornar ViewHolder
            val holder = OrcamentosViewHolder(view)
            return holder
        }

        override fun onBindViewHolder(holder: OrcamentosViewHolder, position: Int) {
            val context = holder.itemView.context

            // recuperar objeto orcamento
            val orcamento = orcamentos[position]

            // atualizar dados de orcamento
            holder.cardNome.text = orcamento.nomeOrcamento

            // adiciona evento de clique
            holder.itemView.setOnClickListener {onClick(orcamento)}
    }
}