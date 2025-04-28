package com.project.financ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.financ.Models.API.HttpRequest;
import com.project.financ.Models.RetornoGastos;
import com.project.financ.Models.Saldo;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    Button btnCadastrar;
    TextView txtSaldoVisor;
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
        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        lista = (ListView)findViewById(R.id.listView);
        txtSaldoVisor = (TextView)findViewById((R.id.txtSaldoVisor));
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                   ClearList(adapter);
                    double valorVisor = 0;
                   if(typegasto == 0){
                       String retorno = "[{\"id\": 1,\"gpId\": \"1\",\"titulo\": \"string\",\"decricao\": \"string\",\"valor\": -200,\"dthr\": \"2025-05-24T19:45:47.267\",\"parcela\": \"1/2\",\"categoria\": \"Crédito\",\"status\": \"Pendente\"},{\"id\": 2,\"gpId\": \"1\",\"titulo\": \"string\",\"decricao\": \"string\",\"valor\": -200,\"dthr\": \"2025-06-24T19:45:47.267\",\"parcela\": \"2/2\",\"categoria\": \"Crédito\",\"status\": \"Pendente\"}]";//HttpRequest.Get("Saldo");
                       Gson gson = new Gson();
                       Type retornoListType = new TypeToken<ArrayList<RetornoGastos>>() {}.getType();
                       ArrayList<RetornoGastos> ret = gson.fromJson(retorno, retornoListType);

                       for(var obj : ret){
                           LocalDateTime data = null;
                           data = LocalDateTime.parse(obj.dthr);
                           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                           String dataFormatada = data.format(formatter);
                           itens.add(obj.titulo + "\n" + obj.descricao  + "\nR$" + obj.valor + "\n" + dataFormatada );

                           valorVisor = valorVisor + obj.valor;
                       }

                   } else if (typegasto == 1) {

                   }
                   else{

                   }
                  //  itens.add("Teste");
                    adapter.notifyDataSetChanged();
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                    txtSaldoVisor.setText("R$ " + decimalFormat.format(valorVisor));

                } catch (Exception e) {
                    ExibeMsg("Erro","Ocorreu um erro:" + e.getMessage());
                }
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Tabela.this, Cadastro.class);
                    startActivity(intent);
                } catch (Exception e) {
                    // Exibe o erro em um AlertDialog para facilitar o diagnóstico
                    new AlertDialog.Builder(Tabela.this)
                            .setTitle("Erro")
                            .setMessage("Ocorreu um erro ao tentar abrir a tela: " + e.getMessage())
                            .setPositiveButton("OK", null)
                            .show();

                    // Também pode imprimir o erro no Logcat
                    e.printStackTrace();
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