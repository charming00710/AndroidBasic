package com.example.volleydemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private TextView responseTextView = null;
    private Button requestButton = null;
    private Button jsonRequestButton = null;
    private Button jsonPostRequestButton = null;
    private Button jsonArrayRequestButton = null;

    private ImageView imageView = null;
    private Button imageButton = null;

    private ProgressDialog dialog = null;

    private RequestQueue requestQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.requestQueue = Volley.newRequestQueue(this);

        this.responseTextView = (TextView) findViewById(R.id.responseText);
        this.requestButton = (Button) findViewById(R.id.requestButton);
        this.requestButton.setOnClickListener(new RequestListener());

        this.jsonRequestButton = (Button) findViewById(R.id.jsonRequestButton);
        this.jsonRequestButton.setOnClickListener(new JsonRequestListener());

        jsonPostRequestButton = (Button) findViewById(R.id.jsonPostRequestButton);
        jsonPostRequestButton.setOnClickListener(new JsonPostRequestListener());

        jsonArrayRequestButton = (Button) findViewById(R.id.jsonArrayButton);
        jsonArrayRequestButton.setOnClickListener(new JsonArrayListener());

        imageView = (ImageView) findViewById(R.id.imageView);
        imageButton = (Button) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new ImageListener());

        Button xmlButton = (Button) findViewById(R.id.xmlButton);
        xmlButton.setOnClickListener(new XmlListener());


        dialog = new ProgressDialog(this);
        dialog.setMessage("loading");

    }

    class RequestListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String url = "http://www.baidu.com";

            StringRequest request = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseTextView.setText(response);
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            responseTextView.setText("error: " + volleyError.getMessage());
                        }
                    }
            );

            requestQueue.add(request);

            System.out.println(dip2px( v.getContext(), 100));
            System.out.println(px2dip( v.getContext(), 100));

        }
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    class JsonRequestListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String tag = "request_json_obj";
            String url = "http://api.androidhive.info/volley/person_object.json";
            dialog.show();
            responseTextView.setText("");
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            responseTextView.setText(jsonObject.toString());
                            dialog.hide();
                        }
                    },

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            responseTextView.setText(volleyError.getMessage());
                            dialog.hide();
                        }
                    }
            );

            request.setTag(tag);
            requestQueue.add(request);
        }


    }

    class JsonPostRequestListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String tag = "request_json_obj";
            String url = "http://api.androidhive.info/volley/person_object.json";
            dialog.show();
            responseTextView.setText("");
            Map<String, Object> params = new HashMap<>();
            params.put("user", "qqq");
            JSONObject jsonObject = new JSONObject(params);
            final JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            responseTextView.setText(jsonObject.toString());
                            dialog.hide();
                        }
                    },

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            responseTextView.setText(volleyError.getMessage());
                            dialog.hide();
                        }
                    }
            );

            request.setTag(tag);
            requestQueue.add(request);
        }
    }

    class JsonArrayListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String url = "http://api.androidhive.info/volley/person_array.json";
            dialog.show();
            JsonArrayRequest request = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            responseTextView.setText(jsonArray.toString());
                            dialog.hide();
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            responseTextView.setText(volleyError.getMessage());
                            dialog.hide();
                        }
                    }
            );

            requestQueue.add(request);

        }
    }

    class ImageListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String url = "http://img1.cache.netease.com/cnews/2016/1/18/2016011814333490cd8.jpg";
            ImageRequest imageRequest = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }

                    , 1024, 1024, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println(volleyError.getMessage());
                        }
                    });

            imageRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));

            requestQueue.add(imageRequest);
        }
    }

    private class XmlListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            QueryResultGenerator generator = new QueryResultGenerator();
            QueryResult queryResult = generator.generateQueryResult(v.getContext(), "test.xml");
            System.out.println(queryResult);
        }
    }
}
