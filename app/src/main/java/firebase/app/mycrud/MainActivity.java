package firebase.app.mycrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import firebase.app.mycrud.model.Persona;

public class MainActivity extends AppCompatActivity {

    EditText nombre, apellido, email, password;
    ListView listaPersona;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Persona> listPersona = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;
    Persona personaSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.txt_nombrePersona);
        apellido = findViewById(R.id.txt_apellidoPersona);
        email = findViewById(R.id.txt_emailPersona);
        password = findViewById(R.id.txt_passwordPersona);
        listaPersona = findViewById(R.id.ls_datePersona);
        inicializarFirebase();
        listarDatos();
        listaPersona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelect = (Persona) parent.getItemAtPosition(position);
                nombre.setText(personaSelect.getNombre());
                apellido.setText(personaSelect.getApellidos());
                email.setText(personaSelect.getEmail());
                password.setText(personaSelect.getPassword());
            }
        });
    }
    
    //INSTANCIA DEL MENU DEL ENCABEZADO
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //METODO PARA DAR FUNCION A LOS BOTONES DEL MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String nombres = nombre.getText().toString();
        String apellidos = apellido.getText().toString();
        String emails = email.getText().toString();
        String pass = password.getText().toString();
        switch (item.getItemId()){
            //BOTON PARA GUARDAR
            case R.id.ic_add:{
                if(nombres.equals("") || apellidos.equals("") || emails.equals("") || pass.equals("")){
                    validacion();
                }
                else{
                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombres);
                    p.setApellidos(apellidos);
                    p.setEmail(emails);
                    p.setPassword(pass);
                    databaseReference.child("Persona").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "guardar", Toast.LENGTH_LONG).show();
                    limpiarCaja();
                }
                break;
            }
            //BOTON PARA ELIMINAR
            case R.id.ic_del:{
                Persona p = new Persona();
                p.setUid(personaSelect.getUid());
                databaseReference.child("Persona").child(p.getUid()).removeValue();
                Toast.makeText(this, "eliminar", Toast.LENGTH_LONG).show();
                limpiarCaja();
                break;
            }
            //BOTON PARA MODIFICAR
            case R.id.ic_mod:{
                if(nombres.equals("") || apellidos.equals("") || emails.equals("") || pass.equals("")){
                    validacion();
                }
                else{
                    Persona p = new Persona();
                    p.setUid(personaSelect.getUid());
                    p.setNombre(nombre.getText().toString().trim());
                    p.setApellidos(apellido.getText().toString().trim());
                    p.setEmail(email.getText().toString().trim());
                    p.setPassword(password.getText().toString().trim());
                    databaseReference.child("Persona").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "modificar", Toast.LENGTH_LONG).show();
                    limpiarCaja();
                }
                break;
            }
            //BOTON PARA LIMPIAR CAJAS DE TEXTO
            case R.id.ic_clean:{
                limpiarCaja();
            }
            default:break;
        }
        return true;
    }
    //METODO PARA VALIDAR CAJAS DE TEXTO
    private void validacion(){
        String nombres = nombre.getText().toString();
        String apellidos = apellido.getText().toString();
        String emails = email.getText().toString();
        String pass = password.getText().toString();

        if(nombres.equals("")){
            nombre.setError("Required");
        }
        else if(apellidos.equals("")){
            apellido.setError("Required");
        }
        else if(emails.equals("")){
            email.setError("Required");
        }
        else if(pass.equals("")){
            password.setError("Required");
        }

    }
    //METODO PARA INICIAR LA BASE DE DATOS
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }
    //METODO PARA LISTAR DATOS
    private void listarDatos(){
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPersona.clear();
                for(DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Persona p = objSnaptshot.getValue(Persona.class);
                    listPersona.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, listPersona);
                    listaPersona.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //METODO PARA LIMPIAR LAS CAJAS DE TEXTO
    private void limpiarCaja(){
        nombre.setText("");
        apellido.setText("");
        email.setText("");
        password.setText("");
    }
    //METODO PARA VOLVER ATRAS CON EL BOTON BACK DEL TELEFONO
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Navigation.class));
    }
}