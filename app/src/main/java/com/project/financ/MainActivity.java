package com.project.financ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.project.financ.Models.Sqlite;

import java.io.File;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File file = new File(this.getCacheDir(), "financ_base.db");

        // Inicializar o banco de dados SQLite usando arquivo customizado
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file, null);

        // Criar tabela no banco de dados
        String createTableQuery = "CREATE TABLE IF NOT EXISTS usuario_tk (id INTEGER PRIMARY KEY, user TEXT, token TEXT, token_refresh TEXT)";
        database.execSQL(createTableQuery);

        // Executar o SELECT
        String query = "SELECT id, user, token, token_refresh FROM usuario_tk where id = 1";
        Cursor cursor = database.rawQuery(query, null);

        // Iterar pelos resultados
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("user"));
                @SuppressLint("Range") String token = cursor.getString(cursor.getColumnIndex("token"));
                @SuppressLint("Range") String tokenRefresh = cursor.getString(cursor.getColumnIndex("token_refresh"));

                // Adicionar os dados ao resultado
                result.append("ID: ").append(id)
                        .append(", User: ").append(user)
                        .append(", Token: ").append(token)
                        .append(", Token Refresh: ").append(tokenRefresh)
                        .append("\n");
            } while (cursor.moveToNext());
        }

        // Fechar o cursor e o banco de dados
        cursor.close();

        // Exibir os dados (por exemplo, em um Toast)
        Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();

        database.close();

        if(result.length() > 0){
            Intent intent = new Intent(MainActivity.this, Tabela.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

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
                File file = new File(getCacheDir(), "financ_base.db");
                SQLiteDatabase database = null;

                try {
                    // Abrir o banco de dados
                    database = SQLiteDatabase.openOrCreateDatabase(file, null);

                    // Inserir dados na tabela
                    database.execSQL("INSERT INTO usuario_tk (user, token, token_refresh) VALUES ('Erick', '123', '142')");

                    // Navegar para outra Activity
                    Intent intent = new Intent(MainActivity.this, Tabela.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    // Exibir mensagem de erro usando um AlertDialog
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Erro")
                            .setMessage("Ocorreu um erro ao tentar realizar a operação: " + e.getMessage())
                            .setPositiveButton("OK", null)
                            .show();

                    // Imprimir o erro no Logcat para depuração
                    e.printStackTrace();
                } finally {
                    // Garantir que o banco de dados seja fechado
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                }
            }
        });

    }
}