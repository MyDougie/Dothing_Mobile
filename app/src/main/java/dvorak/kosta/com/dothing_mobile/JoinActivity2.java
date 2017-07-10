package dvorak.kosta.com.dothing_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String password = intent.getStringExtra("password");

        Button continueBtn2 = (Button) findViewById(R.id.continueBtn2);
        continueBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity3.class);
                String name = ((EditText) findViewById(R.id.name)).getText().toString();
                String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);

                startActivity(intent);
            }
        });

    }
}
