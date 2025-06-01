package com.project.financ;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.financ.Models.API.HttpRequest;
import com.project.financ.Models.DicasReq;
import com.project.financ.Models.RetornoGastos;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Dicas extends AppCompatActivity {
    List<String> dicas;
    ListView listDicas;
    TextView textDica;
    List<DicasReq> dicasArmz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dicas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dicas), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dicas = new ArrayList<>();
        dicasArmz = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dicas);

         textDica = (TextView)findViewById(R.id.editTextDescriDica);
         listDicas = (ListView)findViewById(R.id.listView);
         listDicas.setAdapter(adapter);


        try{
            String retorno = HttpRequest.Get("3002","dicas/retorno");

            Gson gson = new Gson();
            Type retornoListType = new TypeToken<ArrayList<DicasReq>>() {}.getType();

            ArrayList<DicasReq> ret = gson.fromJson(retorno, retornoListType);

            for (var obj : ret) {
                dicasArmz.add(new DicasReq(obj.id,obj.titulo,obj.descricao));
                dicas.add("ID: " + obj.id + "- " + obj.titulo);
            }
            adapter.notifyDataSetChanged();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        }catch (Exception ex){

        }

        listDicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Pega o item clicado, por exemplo: "ID: 1- Título da dica"
                String itemClicado = parent.getItemAtPosition(position).toString();

                // Extrai o ID da string (entre "ID: " e "-")
                int start = itemClicado.indexOf("ID: ") + 4;
                int end = itemClicado.indexOf("-");
                String idStr = itemClicado.substring(start, end).trim();
                int idDica = Integer.parseInt(idStr);

                // Busca a descrição correspondente
                for (DicasReq obj : dicasArmz) {
                    if (obj.id == idDica) {
                        textDica.setText(obj.descricao);
                        break;
                    }
                }
            }
        });
    }

}