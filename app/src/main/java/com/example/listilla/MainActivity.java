package com.example.listilla;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record implements Comparator<Record> {
        public int intents;
        public String nom;

        Record(){

        }

        public Record(int _intents, String _nom ) {
            intents = _intents;
            nom = _nom;
        }

        @Override
        public int compare(Record record, Record t1) {
            return record.intents - t1.intents;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add( new Record(33,"Manolo") );
        records.add( new Record(12,"Pepe") );
        records.add( new Record(42,"Laura") );

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                int[] imagenes = {R.drawable.bobobolechuga,R.drawable.bobobozanahoria,R.drawable.bobobo,R.drawable.donpatch,R.drawable.donpatch2};
                Random random = new Random();
                ((ImageView) convertView.findViewById(R.id.imageView)).setBackgroundResource(imagenes[random.nextInt(imagenes.length)]);
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] nombres = {"Sergio","Albert","Edu","Irene","David","Rafa"};
                Random random = new Random();

                for (int i=0;i<20;i++) {
//                    records.add(new Record(100, "Anonymous"));
                    records.add(new Record(random.nextInt(101),nombres[random.nextInt(nombres.length)]));
                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });

        Button ordena = (Button) findViewById(R.id.ordenaButton);
        ordena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(records,new Record());
                adapter.notifyDataSetChanged();
            }
        });
    }
}