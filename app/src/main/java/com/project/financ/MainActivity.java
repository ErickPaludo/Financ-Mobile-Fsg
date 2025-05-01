package com.project.financ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.financ.Models.API.HttpRequest;
import com.project.financ.Models.API.Login;
import com.project.financ.Models.API.Token;
import com.project.financ.Models.API.TokenStatic;
import com.project.financ.Models.Credito;
import com.project.financ.Models.Debito;
import com.project.financ.Models.RetornoGastos;
import com.project.financ.Models.Saldo;
import com.project.financ.Models.Sqlite;

import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText usernameField;
    EditText passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File file = new File(this.getCacheDir(), "financ_base.db");

        // Inicializar o banco de dados SQLite usando arquivo customizado
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file, null);

        // Criar tabela no banco de dados
        String createTableQuery = "CREATE TABLE IF NOT EXISTS usuario_tk (id, user TEXT, token TEXT, token_refresh TEXT)";
        database.execSQL(createTableQuery);

        // Executar o SELECT
        String query = "SELECT id, user, token, token_refresh FROM usuario_tk";
        Cursor cursor = database.rawQuery(query, null);

        // Iterar pelos resultados
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("user"));
                @SuppressLint("Range") String token = cursor.getString(cursor.getColumnIndex("token"));
                @SuppressLint("Range") String tokenRefresh = cursor.getString(cursor.getColumnIndex("token_refresh"));

                TokenStatic.user = id;
                TokenStatic.token = token;
                TokenStatic.refreshToken = tokenRefresh;
                // Adicionar os dados ao resultado
                result.append("Seja Bem Vindo! ").append(user);

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
        passwordField = (EditText)findViewById((R.id.passwordField));
        usernameField = (EditText)findViewById((R.id.usernameField));
        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!usernameField.getText().toString().isEmpty() && !passwordField.getText().toString().isEmpty()) {
                        File file = new File(getCacheDir(), "financ_base.db");
                        SQLiteDatabase database = null;
                        Login login = new Login(usernameField.getText().toString(),passwordField.getText().toString());
                        String objretorno = HttpRequest.Post(login,"api/Auth/login");

                        if(!objretorno.contains("401")) {
                            objretorno = objretorno.replaceFirst("Sucesso: ","");
                            Gson gson = new Gson();
                            Token ret = gson.fromJson(objretorno, Token.class);


                            // Abrir o banco de dados
                            database = SQLiteDatabase.openOrCreateDatabase(file, null);

                            String sql = "INSERT INTO usuario_tk (id,user, token, token_refresh) VALUES (?,?,?,?)";
                            SQLiteStatement stmt = database.compileStatement(sql);
                            stmt.bindString(1, ret.getUser());
                            stmt.bindString(2, usernameField.getText().toString());
                            stmt.bindString(3, ret.getToken());
                            stmt.bindString(4, ret.getRefreshToken());
                            stmt.executeInsert();

                            // Navegar para outra Activity
                            Intent intent = new Intent(MainActivity.this, Tabela.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            ExibeMsg("","Usuário ou senha incorretos!");
                        }
                    }
                } catch (Exception e) {
                    // Exibir mensagem de erro usando um AlertDialog
                    ExibeMsg("Erro","Ocorreu um erro ao tentar realizar a operação: " + e.getMessage());

                } finally {
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                }
            }
            private void ExibeMsg(String title,String msg){
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(title)
                        .setMessage(msg)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });


    }
}