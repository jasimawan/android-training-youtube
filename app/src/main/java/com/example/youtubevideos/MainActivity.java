package com.example.youtubevideos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String  API_KEY = "AIzaSyDw4hFiPRzDPFy9yAWuM4KqWXvRC4Nkn88";
    RecyclerView recyclerView;
    ArrayList<videoDetails> videoList;
    MyYoutubeAdapter myYoutubeAdapter;
    String[] videoIds = new String[25];
    String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCt9nYeSz90lnOnaVFjxFJzw&maxResults=25&key=AIzaSyDw4hFiPRzDPFy9yAWuM4KqWXvRC4Nkn88";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.youtubeView);
        videoList = new ArrayList<>();

        displayVideo();
    }

    void displayVideo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");


                    for(int i=0 ; i<jsonArray.length(); i++){

                        JSONObject jsonObject1 =  jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                        JSONObject jsonObjectSnippet = jsonObject1.getJSONObject("snippet");

                        JSONObject jsonObjectDefault = jsonObjectSnippet.getJSONObject("thumbnails").getJSONObject("medium");


                        String videoId = jsonVideoId.getString("videoId");
                        String title = jsonObjectSnippet.getString("title");
                        String description = jsonObjectSnippet.getString("description");
                        String url = jsonObjectDefault.getString("url");

                        videoIds[i]  = videoId;


                        videoDetails videoDetails = new videoDetails(videoId , title, description , url);
                        videoList.add(videoDetails);
                    }

                    myYoutubeAdapter = new MyYoutubeAdapter(MainActivity.this, videoList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(myYoutubeAdapter);


                    recyclerView.setAdapter(myYoutubeAdapter);
                    myYoutubeAdapter.notifyDataSetChanged();


                    myYoutubeAdapter.setOnItemClickListener(new MyYoutubeAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {

                            Intent intent = new Intent(MainActivity.this, playerActivity.class);

                            intent.putExtra("videoId", videoIds[position]);

                            startActivity(intent);
                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
            requestQueue.add(stringRequest);
    }
}
