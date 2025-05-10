package com.example.mobile_pf378cya;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ExtracurricularDetailActivity extends AppCompatActivity {

    public JSONObject selected;
    JSONArray jsonArray;
    RecyclerView member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extracurricular_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView lblNameDetail = findViewById(R.id.lblNameDetail);
        TextView lblDescription = findViewById(R.id.lblDescriptionDetail);
        TextView lblDate = findViewById(R.id.lblDateDetail);
        TextView lblLeader = findViewById(R.id.lblNameLeaderDetail);
        ImageView iconName = findViewById(R.id.detailImg);
        ImageView btnBack = findViewById(R.id.backDetail);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExtracurricularDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


        String getIntent = getIntent().getStringExtra("item");
        try {
//            selected = new JSONObject(getIntent);
//            String selectedId = selected.getString("id");
            APIHelper helper = new APIHelper("http://10.0.2.2:5000/api/ExtracurricularDetail/" + getIntent, "GET");
            try {
                String datadetail = helper.execute().get();
                JSONObject jsonObject = new JSONObject(datadetail);

                String nameDetail = jsonObject .getString("name");
                String description = jsonObject.getString("description");
                String date = jsonObject.getString("day") + "," + jsonObject.getString("startTime") + "-" + jsonObject.getString("endTime");
                String leader = jsonObject.getJSONObject("leader").getString("fullname");
                String iconImage = jsonObject.getString("iconName");

                lblNameDetail.setText(nameDetail);
                lblDescription.setText(description);
                lblDate.setText(date);
                lblLeader.setText(leader);

                String imageUrl = "http://10.0.2.2:5000/images/" + iconImage + ".png";
                // Load image in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());

                            // Update UI thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iconName.setImageBitmap(bitmap);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}