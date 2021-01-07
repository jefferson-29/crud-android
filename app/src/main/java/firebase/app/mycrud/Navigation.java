package firebase.app.mycrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Navigation extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Button recordatorio = findViewById(R.id.recordatorio);
        Button curriculum = findViewById(R.id.curriculum);
        Button crud = findViewById(R.id.crud);
        Button otros = findViewById(R.id.otros);

        recordatorio.setOnClickListener(this);
        curriculum.setOnClickListener(this);
        crud.setOnClickListener(this);
        otros.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.recordatorio:{
                Toast.makeText(this, "recordatorios", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.curriculum:{
                Intent intent = new Intent(this, Curriculum.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.crud:{
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.otros:{
                Toast.makeText(this, "otros", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}