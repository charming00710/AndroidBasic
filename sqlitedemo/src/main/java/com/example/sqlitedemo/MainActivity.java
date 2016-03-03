package com.example.sqlitedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sqlitedemo.domain.User;
import com.example.sqlitedemo.domain.dao.UserDao;


public class MainActivity extends Activity {
    private Button saveButton = null;
    private Button showButton = null;
    private EditText userNameEditor = null;
    private EditText ageEditor = null;
    private UserDao userDao = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = new UserDao(this);
        userNameEditor = (EditText) findViewById(R.id.username);
        ageEditor = (EditText) findViewById(R.id.age);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditor.getText().toString();
                Integer age = Integer.parseInt(ageEditor.getText().toString());

                User user = new User();
                user.setUserName(userName);
                user.setAge(age);

                userDao.saveUser(user);
            }
        });

        showButton = (Button) findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String userName = "zcm";
                  User user = userDao.getByUserName( userName );


                  if ( user == null ) {
                      return;
                  }

                  TextView userInfo = (TextView) findViewById( R.id.userInfo );
                  userInfo.setText( user.getUserName() + ":" + user.getAge() );
              }
          }
        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
