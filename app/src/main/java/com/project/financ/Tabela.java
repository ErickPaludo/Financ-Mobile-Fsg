package com.project.financ;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.financ.Models.API.HttpRequest;
import com.project.financ.Models.API.TokenStatic;
import com.project.financ.Models.RetornoGastos;
import com.project.financ.Models.Saldo;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tabela extends AppCompatActivity {
    int typegasto = 0;
    List<String> itens;
    ListView lista;
    Button btnPesquisar;
    Button btnCadastrar;
    Button btnSair;
    TextView txtSaldoVisor;
    Spinner combo;
    EditText btnDtIni;
    EditText btnDtFim;
    String[] itensCombo = {"Todos", "Saldo", "Débito", "Crédito"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabela);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tabela), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    itensCombo
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            combo.setAdapter(adapter);
            return insets;
        });


        btnPesquisar = (Button)findViewById((R.id.btnPesquisar));

        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        lista = (ListView)findViewById(R.id.listView);
        txtSaldoVisor = (TextView)findViewById((R.id.txtSaldoVisor));
        itens = new ArrayList<>(Arrays.asList());
        combo = (Spinner)findViewById(R.id.combo);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itens);
        lista.setAdapter(adapter);
        btnDtIni = (EditText)findViewById(R.id.btnDtIni);
        btnDtFim = (EditText)findViewById(R.id.btnDtFim);
        btnSair = (Button) findViewById(R.id.btnSair);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = new File(getCacheDir(), "financ_base.db");
                    SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file, null);

                    // Deletar registros da tabela
                    String whereClause = "id = ?";
                    String[] whereArgs = new String[] {TokenStatic.getUser()};

                    int rowsDeleted = database.delete("usuario_tk", whereClause, whereArgs);

                    System.out.println("Linhas deletadas: " + rowsDeleted);

                    // Fechar o banco de dados
                    database.close();

                    Intent intent = new Intent(Tabela.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        btnDtIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Abrir o DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Tabela.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Configurar a data selecionada no EditText
                            String data = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                            btnDtIni.setText(data);
                        },
                        year,
                        month,
                        day
                );

                datePickerDialog.show();
            }
        });
        btnDtFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Abrir o DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Tabela.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Configurar a data selecionada no EditText
                            String data = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                            btnDtFim.setText(data);
                        },
                        year,
                        month,
                        day
                );

                datePickerDialog.show();
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
                       String retorno = HttpRequest.Get("");
                       Gson gson = new Gson();
                       Type retornoListType = new TypeToken<ArrayList<RetornoGastos>>() {}.getType();
                       ArrayList<RetornoGastos> ret = gson.fromJson(retorno, retornoListType);

                       for(var obj : ret){
                           LocalDateTime data = null;
                           data = LocalDateTime.parse(obj.dthr);
                           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                           String dataFormatada = data.format(formatter);
                           if(obj.categoria.equals("c")) {
                               itens.add(obj.titulo + "\n" + obj.descricao  + "\nR$" + obj.valor + "\n" + dataFormatada );
                           }
                           else {
                               itens.add("ID: " + obj.gpId + "\n" + obj.titulo + "\n" + obj.descricao  + "\nParcelas: " + obj.parcela + "\nR$" + obj.valor + "\n" + dataFormatada );
                           }
                           valorVisor = valorVisor + obj.valor;
                       }

                   } else if (typegasto == 1) {
                       String retorno = HttpRequest.Get("&categoria=S");
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
                   }
                   else if (typegasto == 2){
                       String retorno = HttpRequest.Get("&categoria=D");
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
                   }
                   else{
                       String retorno = HttpRequest.Get("&categoria=C");
                       Gson gson = new Gson();
                       Type retornoListType = new TypeToken<ArrayList<RetornoGastos>>() {}.getType();
                       ArrayList<RetornoGastos> ret = gson.fromJson(retorno, retornoListType);

                       for(var obj : ret){
                           LocalDateTime data = null;
                           data = LocalDateTime.parse(obj.dthr);
                           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                           String dataFormatada = data.format(formatter);
                           itens.add("ID: " + obj.gpId + "\n" + obj.titulo + "\n" + obj.descricao  + "\nParcelas: " + obj.parcela + "\nR$" + obj.valor + "\n" + dataFormatada );
                           valorVisor = valorVisor + obj.valor;
                       }
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

        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelecionado = parent.getItemAtPosition(position).toString();
               if(itemSelecionado.equals("Todos")) {
               typegasto = 0;
               }
               else if(itemSelecionado.equals("Saldo")){
                   typegasto = 1;
               }
               else if(itemSelecionado.equals("Débito")){
                   typegasto = 2;
               }
               else if(itemSelecionado.equals("Crédito")){
                   typegasto = 3;
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typegasto = 0;
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