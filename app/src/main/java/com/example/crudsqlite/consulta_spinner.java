package com.example.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class consulta_spinner extends AppCompatActivity {

    private Spinner sp_options;
    private TextView tv_cod, tv_descripcion, tv_precio;

    //ArrayList<String>listaArticulos;
    //ArrayList<Dto>articulosList;

    ConexionSQLite conexion = new ConexionSQLite(this);
    Dto datos = new Dto();

    private FloatingActionMenu menu;
    private FloatingActionButton item1, item2, item3;

    modal_Toast_Custom modal = new modal_Toast_Custom();


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_close)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea salir?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        //un listener que at pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
        /*lntent intent —— new Intent(DashboardLuces.this, luces control sms.class), startActivity(intent),’”/
        //MainActivity.this.finishAffinity(),
        //finish()*/
                            finishAffinity();
                        }
                    }).show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento at listener habitual
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_spinner);


        sp_options = (Spinner)findViewById(R.id.sp_options);
        tv_cod = (TextView)findViewById(R.id.tv_cod);
        tv_descripcion = (TextView)findViewById(R.id.tv_descripcion);
        tv_precio = (TextView)findViewById(R.id.tv_precio);

        menu = findViewById(R.id.menu_fab);
        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);

        menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened){
                    Toast.makeText(consulta_spinner.this, "Menu Abierto", Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(consulta_spinner.this, "Menu Cerrado", Toast.LENGTH_SHORT);
                }
            }
        });

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (menu.isOpened()) {
                        menu.close(true);
                    }
                }
            });

            item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  finish();
                    Intent intent = new Intent(consulta_spinner.this, MainActivity.class);
                    startActivity(intent);

                   */
                  modal.dialogConfirmCustom2(consulta_spinner.this);
                }
            });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modal.dialogAbout(consulta_spinner.this);
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });


        //conexion. obtenerListaArticulos(),
        conexion.consultaListaArticulos();


        //ArrayAdapter<CharSequence> adaptador —— new ArrayAdapter(this, android. R.layout. simple spinner item, IistaArticulos);
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, conexion.obtenerListaArticulos());
        sp_options.setAdapter(adaptador);


        sp_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                       long I) {

                if (position != 0) {
                    tv_cod.setText("Codigo: " + conexion.consultaListaArticulos().get(position - 1).getCodigo());
                    tv_descripcion.setText("Descripcion: " + conexion.consultaListaArticulos().get(position - 1).getDescripcion());
                    tv_precio.setText("Precio: " + String.valueOf(conexion.consultaListaArticulos().get(position - 1).getPrecio()));

                } else {
                    tv_cod.setText("Codigo: ");
                    tv_descripcion.setText("Descripcion: ");
                    tv_precio.setText("Precio: ");

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

}