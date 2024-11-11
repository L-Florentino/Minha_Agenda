package com.example.minha_agenda.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minha_agenda.R;

public class ViewHolder_Nota extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolder_Nota.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position); // Executa quando o item é clicado
        void onItemLongClick(View view, int position); // Executa quando o item é clicado e mantido pressionado
    }

    public void setOnClickListener(ViewHolder_Nota.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public ViewHolder_Nota(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getBindingAdapterPosition());
                return false;
            }
        });
    }

    private int getBindingAdapterPosition() {
        return 0;
    }

    public void ColetarDados(Context context, String id_nota, String uid_usuario, String email_usuario,
                             String data_hora_atual, String titulo, String descricao, String data_nota, String estado) {
        // Declarando as variáveis
        TextView Id_nota_Item, Uid_Usuario_Item, Email_usuario_Item, Data_hora_registro_Item, Titulo_Item,
                Descricao_Item, Data_Item, Estado_Item;

        // Estabelecer a conexão com o item
        Id_nota_Item = mView.findViewById(R.id.Id_nota_Item);
        Uid_Usuario_Item = mView.findViewById(R.id.Uid_Usuario_Item);
        Email_usuario_Item = mView.findViewById(R.id.Email_usuario_Item);
        Data_hora_registro_Item = mView.findViewById(R.id.Data_hora_registro_Item);
        Titulo_Item = mView.findViewById(R.id.Titulo_Item);
        Descricao_Item = mView.findViewById(R.id.Descricao_Item);
        Data_Item = mView.findViewById(R.id.Data_Item);
        Estado_Item = mView.findViewById(R.id.Estado_Item);

        // Exibir a informação dentro do item
        Id_nota_Item.setText(id_nota);
        Uid_Usuario_Item.setText(uid_usuario);
        Email_usuario_Item.setText(email_usuario);
        Data_hora_registro_Item.setText(data_hora_atual);
        Titulo_Item.setText(titulo);
        Descricao_Item.setText(descricao);
        Data_Item.setText(data_nota);
        Estado_Item.setText(estado);
    }
}
