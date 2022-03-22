package localstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yzm3214_2.R;

import java.util.List;

public class SqliteActivity extends AppCompatActivity {
    private EditText et;
    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        tv = findViewById(R.id.tv);

        SQLiteDB db = new SQLiteDB(SqliteActivity.this);


        List<String> veriler = db.Read();
        if(veriler.isEmpty()){
            tv.setText("DB bos");
        } else{
            String icerik = "";
            for(int i = 0; i < veriler.size(); i++){
                icerik += veriler.get(i);
                icerik += "\n";
            }
            tv.setText(icerik);
        }
        db.getReadableDatabase();
        Toast.makeText(this, String.valueOf(veriler.size()), Toast.LENGTH_SHORT).show();

        et = findViewById(R.id.et);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.Insert(et.getText().toString());
            }
        });


    }
}