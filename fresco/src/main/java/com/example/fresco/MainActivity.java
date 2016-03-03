package com.example.fresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends Activity {
    SimpleDraweeView simpleDraweeView = null;
    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);
        Log.d("yy", "simpleDraweeView");
//        simpleDraweeView.setImageURI(Uri.parse("http://f2.topit.me/2/79/0a/1175191760a730a792o.jpg"));
        simpleDraweeView.setImageURI(Uri.parse("http://i1.res.meizu.com/fileserver/ad/img/497/94af15555645402b8ce2c0da16027b06.png"));

//        draweeView.setImageURI(uri);

        textView = (TextView) findViewById(R.id.formHtml);
        textView.setText(Html.fromHtml("(X+Y)<SUP>2</SUP>"));

    }

}
