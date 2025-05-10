package com.example.mobile_pf378cya;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recView = findViewById(R.id.homeRecViewItemExtracurricular);
        LoadData();

//        TabLayout tabLayout = findViewById(R.id.tabLayout);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                if (position == 0) {
//                    // Tetap di HomeActivity, tampilkan Fragment Extracurricular
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.tabLayout, new ExtracurricularFragment())
//                            .commit();
//                } else if (position == 1) {
//                    // Pindah ke AccountScreenActivity
//                    Intent intent = new Intent(HomeActivity.this, AccountScreenActivity.class);
//                    startActivity(intent);
//
//                    // Opsional: Pilih kembali tab pertama (agar tidak tetap di tab kedua di HomeActivity)
//                    TabLayout.Tab firstTab = tabLayout.getTabAt(0);
//                    if (firstTab != null) {
//                        firstTab.select();
//                    }
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {}
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {}
//        });



    }

    private void LoadData(){
        APIHelper helper = new APIHelper("http://10.0.2.2:5000/api/Extracurriculars", "GET");

        try {
            JSONArray dataEkskuldariAPI = new JSONArray(helper.execute().get());
            recView.setAdapter(new HomeRevViewAdapter(dataEkskuldariAPI));
            recView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}