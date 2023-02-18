package com.example.pm2e14586.adaps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pm2e14586.R.layout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pm2e14586.ViewActivity;
import com.example.pm2e14586.R;
import com.example.pm2e14586.entidades.Contacto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListContacAdapter extends RecyclerView.Adapter<ListContacAdapter.ContactoViewHolder> {

    ArrayList<Contacto> listaContactos;
    ArrayList<Contacto> listaOriginal;


    public ListContacAdapter(ArrayList<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);

    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.lista_item_contacto, null, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.viewNombre.setText(listaContactos.get(position).getNombre());
        holder.viewTelefono.setText(listaContactos.get(position).getTelefono());
        holder.viewPais.setText(listaContactos.get(position).getPais());
        holder.viewNota.setText(listaContactos.get(position).getNota());


    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Contacto> collection = listaContactos.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaContactos.clear();
                listaContactos.addAll(collection);
            } else {
                for (Contacto c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaContactos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewTelefono, viewNota, viewPais;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.txtNombre);
            viewTelefono = itemView.findViewById(R.id.txtTelefono);
            viewNota = itemView.findViewById(R.id.viewNota);
            viewPais = itemView.findViewById(R.id.viewPais);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ViewActivity.class);
                    intent.putExtra("ID", listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }

    }

}
