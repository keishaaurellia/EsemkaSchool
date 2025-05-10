package com.example.mobile_pf378cya;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHelper extends AsyncTask <String, Void, String> {

    String url;
    String method;
    HttpURLConnection connection;


    //Alt+ Insert atau klik kanan = Constractor
    public APIHelper(String url, String method){
        this.url = url;
        this.method = method;


//this.connection = new URL(this.url).openConnection();

        try{
            this.connection = (HttpURLConnection) new URL(this.url).openConnection();
            this.connection.setRequestMethod(this.method);
            this.connection.setRequestProperty("Content-Type", "application/json");
        }catch  (IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        if(strings.length > 0){
            try {
                DataOutputStream outputStream = new DataOutputStream(this.connection.getOutputStream());
                outputStream.writeBytes(strings[0]);
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //Baca data yang dikirim API
        String result = "";


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));

            result = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}


//    String url;
//    String method;
//    HttpURLConnection connection;
//    public APIHelper(String url, String method) {
//        this.url = url;
//        this.method = method;
//        try {
//            this.connection = (HttpURLConnection) new URL(this.url).openConnection();
//            this.connection.setRequestMethod(this.method);
//            this.connection.setRequestProperty("Content-Type", "application/json");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected String doInBackground(String... inputs) {
//
//        if(inputs.length > 0){
//            //input menuju API
//
//            try {
//                DataOutputStream sender = new DataOutputStream(this.connection.getOutputStream());
//                sender.writeBytes(inputs[0]);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        String result = "";
//        BufferedReader reader;
//
//        try {
//             reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
//
//        } catch (IOException e) {
//             reader = new BufferedReader(new InputStreamReader(this.connection.getErrorStream()));
//        }
//
//        while(true){
//            String terbaca = null;
//            try {
//                terbaca = reader.readLine();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            if(terbaca == null){
//                break;
//            }
//
//            result += terbaca;
//        }
//
//        return result;
//
//
//
//    }
//}
