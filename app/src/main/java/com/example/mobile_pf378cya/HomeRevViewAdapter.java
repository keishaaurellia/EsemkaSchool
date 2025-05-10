package com.example.mobile_pf378cya;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeRevViewAdapter extends RecyclerView.Adapter<HomeRevViewAdapter.ViewHolder> {

    JSONArray dataEkskul;

    public HomeRevViewAdapter(JSONArray dataEkskul) {
        this.dataEkskul = dataEkskul;
    }

    @NonNull
    @Override
    public HomeRevViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extracurricular,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRevViewAdapter.ViewHolder holder, int position) {
        View layoutHome = holder.itemView;

        ImageView img = layoutHome.findViewById(R.id.homeImageItemExtracuricular);
        TextView lblName = layoutHome.findViewById(R.id.homeLblNameItemExtracuricullar);


        try {
            JSONObject ekskul = dataEkskul.getJSONObject(position);
            String namaEkskul = ekskul.getString("name");
//            String namaIcon = ekskul.getString("iconName");

            lblName.setText(namaEkskul);

//            String urlIcon = "http://10.0.2.2:5000/images/" + namaIcon + ".png";
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    try {
//
//                        HttpURLConnection url = (HttpURLConnection) new URL(urlIcon).openConnection();
//                        Bitmap bitmap = BitmapFactory.decodeStream(url.getInputStream());
//
//                        HomeActivity homeActivity = (HomeActivity) layoutHome.getContext();
//                        homeActivity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                img.setImageBitmap(bitmap);
//                            }
//                        });
//
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                }
//            }).start();

            CardView cardExtracurriculer = layoutHome.findViewById(R.id.cardExtracurriculer);
            cardExtracurriculer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(layoutHome.getContext(), ExtracurricularDetailActivity.class);
                    intent.putExtra("item", ekskul.toString());
                    layoutHome.getContext().startActivity(intent);
                }
            });

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getItemCount() {
        return dataEkskul.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
