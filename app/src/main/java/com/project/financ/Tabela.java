package com.project.financ;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.financ.Models.API.HttpRequest;
import com.project.financ.Models.Saldo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tabela extends AppCompatActivity {
    int typegasto = 0;
    List<String> itens;
    ListView lista;
    Button btnPesquisar;
    Button btnSaldo;
    Button btnDebito;
    Button btnCredito;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabela);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tabela), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnPesquisar = (Button)findViewById((R.id.btnPesquisar));
        btnSaldo = (Button)findViewById(R.id.btnSaldo);
        btnDebito = (Button)findViewById(R.id.btnDebito);
        btnCredito = (Button)findViewById(R.id.btnCredito);
        lista = (ListView)findViewById(R.id.listView);
        itens = new ArrayList<>(Arrays.asList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itens);
        lista.setAdapter(adapter);

        btnSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typegasto = 0;
                //  ExibeMsg("Debug","Tipo " + typegasto);
                btnSaldo.setBackgroundColor(Color.parseColor("#673AB7"));
                btnDebito.setBackgroundColor(Color.parseColor("#C8C8C8"));
                btnCredito.setBackgroundColor(Color.parseColor("#C8C8C8")); // Define uma cor em hexadecimal

            }
        });
        btnDebito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typegasto = 1;
                //    ExibeMsg("Debug","Tipo " + typegasto);
                btnDebito.setBackgroundColor(Color.parseColor("#673AB7"));
                btnSaldo.setBackgroundColor(Color.parseColor("#C8C8C8"));
                btnCredito.setBackgroundColor(Color.parseColor("#C8C8C8")); // Define uma cor em hexadecimal
            }
        });
        btnCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typegasto = 2;
                //   ExibeMsg("Debug","Tipo " + typegasto);
                btnCredito.setBackgroundColor(Color.parseColor("#673AB7"));
                btnDebito.setBackgroundColor(Color.parseColor("#C8C8C8"));
                btnSaldo.setBackgroundColor(Color.parseColor("#C8C8C8")); // Define uma cor em hexadecimal
            }
        });


        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   ClearList(adapter);

                   if(typegasto == 0){
                       String retorno = HttpRequest.Get("Saldo");
                       Gson gson = new Gson();
                       Type saldoListType = new TypeToken<ArrayList<Saldo>>() {}.getType();
                       ArrayList<Saldo> saldo = gson.fromJson(retorno, saldoListType);

                       for(var obj : saldo){itens.add(obj.titulo + "|R$" + obj.valor + "|" + obj.dthrReg + "|" + obj.descricao);

                       }

                   } else if (typegasto == 1) {

                   }
                   else{

                   }

                    itens.add("e");

                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    ExibeMsg("Erro","Ocorreu um erro:" + e.getMessage());
                }
            }
        });
    }
    private void ExibeMsg(String title,String msg){
        new AlertDialog.Builder(Tabela.this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
    private void ClearList(ArrayAdapter<String> adapter){
        if (adapter != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }
}