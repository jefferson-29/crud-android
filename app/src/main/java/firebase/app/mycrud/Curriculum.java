package firebase.app.mycrud;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class Curriculum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
    }
    //METODO PARA VOLVER ATRAS CON EL BOTON BACK DEL TELEFONO
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Navigation.class));
    }
}