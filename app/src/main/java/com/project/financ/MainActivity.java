package com.project.financ;

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

import com.project.financ.Models.API.HttpRequest;
import com.project.financ.Models.Credito;
import com.project.financ.Models.Debito;
import com.project.financ.Models.Saldo;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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
                    if (typegasto == 0) {
                        Saldo obj = new Saldo(0, textTitle.getText().toString(), textDesc.getText().toString(), Double.parseDouble(textValor.getText().toString()), LocalDateTime.now(), "0", 1);
                        Saldo.Cadastro(obj);
                    } else if (typegasto == 1) {
                        Debito obj = new Debito(0, textTitle.getText().toString(), textDesc.getText().toString(), Double.parseDouble(textValor.getText().toString()), LocalDateTime.now(), "0", 1);
                        Debito.Cadastro(obj);
                    } else {
                        Credito obj = new Credito(0, textTitle.getText().toString(), textDesc.getText().toString(), Double.parseDouble(textValor.getText().toString()), LocalDateTime.now(), "0", 1, Double.parseDouble(textValor.getText().toString()),LocalDateTime.now(), Integer.parseInt(textParcelas.getText().toString()));
                        Credito.Cadastro(obj);
                    }
                }
                else{
                    ExibeMsg("Erro","Entrada de dados Inválidas");
                }
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
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}