package com.example.repository_test;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText nameText;

    private EditText ageText;

    private TextView resultText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_main);

        nameText = (EditText) this.findViewById(R.id.name);
        ageText = (EditText) this.findViewById(R.id.age);
        resultText = (TextView) this.findViewById(R.id.showText);

        Button saveButton = (Button) this.findViewById(R.id.button);
        Button showButton = (Button) this.findViewById(R.id.showButton);

        saveButton.setOnClickListener( listener );
        showButton.setOnClickListener( listener );

    }

    private OnClickListener listener = new OnClickListener() {
        public void onClick(View v) {
            Button button = (Button) v;

            SharedPreferences sharedPreferences = getSharedPreferences( "zcm",
                    Context.MODE_PRIVATE);

            switch (button.getId()) {
                case R.id.button:
                    String name = nameText.getText().toString();
                    int age = Integer.parseInt( ageText.getText().toString() );

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);

                    editor.putInt( "age", age);
                    editor.commit();

                    break;

                case R.id.showButton:
                    String nameValue = sharedPreferences.getString( "name", "");
                    int ageValue = sharedPreferences.getInt("age", 1);

                    resultText.setText("姓名：" + nameValue + "，年龄：" + ageValue);
                    break;

            }
        }
    };
}
