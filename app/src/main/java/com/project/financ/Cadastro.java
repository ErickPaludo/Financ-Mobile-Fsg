package com.project.financ;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.financ.Models.API.TokenStatic;
import com.project.financ.Models.Credito;
import com.project.financ.Models.Debito;
import com.project.financ.Models.Saldo;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

public class Cadastro extends AppCompatActivity {

    int typegasto = 0; //0 Saldo / 1 Debito /2 Credito
    EditText textTitle;
    EditText textValor;
    EditText textData;
    EditText textValorParcela;
    EditText textDesc;
    EditText textParcelas;
    Button btnSaldo;
    Button btnDebito;
    Button btnCredito;
    Button btnSalvar;
    Button btnVizualizar;
    Button btnDicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        Calendar hoje = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
        String dataAtual = formato.format(hoje.getTime());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cadastro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textTitle = (EditText)findViewById(R.id.textTitle);
        textValor = (EditText)findViewById(R.id.textValor);
        textData = (EditText)findViewById(R.id.textData);
        textValorParcela = (EditText)findViewById(R.id.textValorParcela);
        textDesc = (EditText)findViewById(R.id.textDesc);
        textParcelas = (EditText)findViewById(R.id.textParcelas);

        btnSaldo = (Button)findViewById(R.id.btnSaldo);
        btnDebito = (Button)findViewById(R.id.btnDebito);
        btnCredito = (Button)findViewById(R.id.btnCredito);
        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnVizualizar = (Button)findViewById(R.id.btnVizualizar);
        btnDicas = (Button)findViewById(R.id.btnDicas);

        textData.setText(dataAtual);
        btnSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typegasto = 0;
                //  ExibeMsg("Debug","Tipo " + typegasto);
                btnSaldo.setBackgroundColor(Color.parseColor("#673AB7"));
                btnDebito.setBackgroundColor(Color.parseColor("#C8C8C8"));
                btnCredito.setBackgroundColor(Color.parseColor("#C8C8C8")); // Define uma cor em hexadecimal

                textValorParcela.setVisibility(View.GONE);
                textParcelas.setVisibility(View.GONE);
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

                textValorParcela.setVisibility(View.GONE);
                textParcelas.setVisibility(View.GONE);
            }
        });
        textData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Abrir o DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Cadastro.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Configurar a data selecionada no EditText
                            String data = String.format("%02d/%02d/%d", selectedMonth + 1,selectedDay, selectedYear);
                            textData.setText(data);
                        },
                        year,
                        month,
                        day
                );

                datePickerDialog.show();
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

                textValorParcela.setVisibility(View.VISIBLE);
                textParcelas.setVisibility(View.VISIBLE);
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Object objHttp = new Object();
                if (Validainput(textTitle.getText().toString(), textValor.getText().toString(), textValorParcela.getText().toString(),textParcelas.getText().toString(), typegasto)) {

                    String textDataStr = textData.getText().toString(); // MM/dd/yyyy

                    // Definir formato de entrada
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate dataLocal = LocalDate.parse(textDataStr, inputFormatter);

                    // Adicionar um horário padrão (meio-dia, por exemplo)
                    LocalDateTime dataCompleta = dataLocal.atTime(12, 0);

                    // Definir formato de saída para ISO 8601
                    DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    String dataIso = dataCompleta.atOffset(ZoneOffset.UTC).format(isoFormatter);


                    if (typegasto == 0) {
                        Saldo obj = new Saldo(0, textTitle.getText().toString(), textDesc.getText().toString(), Double.parseDouble(textValor.getText().toString()), dataIso, "0", TokenStatic.getUser());
                        Saldo.Cadastro(obj);
                    } else if (typegasto == 1) {
                        Debito obj = new Debito(0, textTitle.getText().toString(), textDesc.getText().toString(), Double.parseDouble(textValor.getText().toString()), dataIso, "0", TokenStatic.getUser());
                        Debito.Cadastro(obj);
                    } else {
                        if(textValorParcela.getText().toString().isEmpty()){
                            textValorParcela.setText("0.01");
                        }
                        else{
                            textValor.setText("0.01");
                        }
                        Credito obj = new Credito(0, textTitle.getText().toString(), textDesc.getText().toString(), Double.parseDouble(textValorParcela.getText().toString()), dataIso, "0", TokenStatic.getUser(), Double.parseDouble(textValor.getText().toString()),LocalDateTime.now(), Integer.parseInt(textParcelas.getText().toString()));
                        Credito.Cadastro(obj);
                    }
                    textTitle.setText("");
                    textDesc.setText("");
                    textData.setText(dataAtual);
                    textValor.setText("");
                    textValorParcela.setText("");
                }
                else{
                    ExibeMsg("Erro","Entrada de dados Inválidas");
                }
            }
        });
        btnVizualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Cadastro.this, Tabela.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
            }
        });
        btnDicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Cadastro.this, Dicas.class);
                    startActivity(intent);
            }
        });
    }
    private boolean Validainput(String titulo,String valor,String valorParcela,String parcela,int tipo) {
        boolean sucess = false;
        if(!titulo.isEmpty()){
            if(tipo == 2){
                if(!valor.isEmpty() || !valorParcela.isEmpty()){
                    if(!parcela.isEmpty()){
                        sucess = true;
                    }
                }
            }
            else{
                if(!valor.isEmpty()) {
                    sucess = true;
                }
            }
        }
        return sucess;
    }
    private void ExibeMsg(String title,String msg){
        new AlertDialog.Builder(Cadastro.this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}