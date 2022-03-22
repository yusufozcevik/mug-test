package localstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yzm3214_2.R;

public class SharedPrefsActivity extends AppCompatActivity {
    private EditText et;
    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        String s = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString(getResources().getString(R.string.shared_prefs_key), "dosya bos");
        tv = findViewById(R.id.tv);
        tv.setText(s);

        et = findViewById(R.id.et);

        btn = findViewById(R.id.btn);
        btn.setText("SİL");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSharedPreference(SharedPrefsActivity.this,getResources().getString(R.string.shared_prefs_key));
            }
        });




    }

    public void setSharedPreference(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void removeSharedPreference(Context context, String key){
        SharedPreferences sharedPref =
                context.getSharedPreferences(context.getPackageName(),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.clear();
        edit.apply();
    }
}