package carteira.kohatsu.com.carteiradeclientes.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import carteira.kohatsu.com.carteiradeclientes.ActCadCliente;
import carteira.kohatsu.com.carteiradeclientes.R;
import carteira.kohatsu.com.carteiradeclientes.entities.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderCliente> {

    private List<Cliente> dados;


    public ClienteAdapter(List<Cliente> dados){
        this.dados = dados;
    }

    @NonNull
    @Override
    public ClienteAdapter.ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.linha_clientes, viewGroup, false);

        ViewHolderCliente holder = new ViewHolderCliente(view, viewGroup.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.ViewHolderCliente viewHolder, int i) {
        if((dados != null) && (dados.size() > 0)){
            Cliente cliente = dados.get(i);

            viewHolder.txtNome.setText(cliente.getNome());
            viewHolder.txtTelefone.setText(cliente.getTelefone());
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }


    public class ViewHolderCliente extends RecyclerView.ViewHolder{

        public TextView txtNome, txtTelefone;

        public ViewHolderCliente(@NonNull View itemView, final Context context) {
            super(itemView);

            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtTelefone = (TextView) itemView.findViewById(R.id.txtTelefone);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(dados.size() > 0){
                        Cliente cliente = dados.get(getLayoutPosition());

                        Intent intent = new Intent(context, ActCadCliente.class);
                        intent.putExtra("cliente", cliente);
                        ((AppCompatActivity) context).startActivityForResult(intent, 0);
                    }


                }

            });
        }
    }

}
