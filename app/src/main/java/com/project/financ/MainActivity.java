package com.project.financ;

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

import com.project.financ.Models.API.HttpRequest;
import com.project.financ.Models.Credito;
import com.project.financ.Models.Debito;
import com.project.financ.Models.Saldo;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    Button loginButton;

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

        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, Tabela.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } catch (Exception e) {
                    // Exibe o erro em um AlertDialog para facilitar o diagnóstico
                    new AlertDialog.Builder(MainActivity.this)
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
}