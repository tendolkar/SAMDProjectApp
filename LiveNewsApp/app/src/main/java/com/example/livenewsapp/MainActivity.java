package com.example.livenewsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

ArrayList<NewsItem> NewsItemList;
ListView listView;
    NewsAdapter adapter;
    String src ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewsItemList=new ArrayList();
        listView=(ListView) findViewById(R.id.listview_news);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("https://www.suchtv.pk/latest-news.feed", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(MainActivity.this,"data receive succesfully"+response,Toast.LENGTH_SHORT).show();
                Document doc = Jsoup.parse(response);

                Elements itemElement = doc.getElementsByTag("item");

                 for (int i = 0; i <itemElement.size() ; i++) {
                    Element item =itemElement.get(i);

                    String title =removeCDate(item.child(0).text());
                    String link=removeCDate(item.child(1).text());
                    String guid =removeCDate(item.child(2).text());
                  //  String comments =removeCDate(item.child(2).text());








                    //String category =removeCDate(item.child(5).text());

                    String description =removeCDate(item.child(3).text());
                    //String dcCreater =removeCDate(item.child().text());
                    String pubDate =removeCDate(item.child(6).text());

                     Document doc2 = Jsoup.parse(description);
                     String desc = doc2.getElementsByTag("p").text();
                     String image = doc2.getElementsByTag("img").attr("src");
                     //String image = item.getElementsByTag("image").tagName("img").attr("src");


                    NewsItem news = new NewsItem();
                    news.title=title;
                    news.link=link;
                    news.guid=guid;
                    news.image =image;
                    news.date=pubDate;
                    news.description=desc;


        //news.src=src;
                    //news.dcC=dcCreater;
                    //news.comments=comments;
                    //news.slC=slashComments;

                    NewsItemList.add(news);

                    Log.i("myTag: ","title: "+title);
                    Log.i("myTag: ","link: "+link);
                    //Log.i("myTag: ","comments: "+comments);
                    Log.i("myTag: ","pubDate: "+pubDate);
                   // Log.i("myTag: ","dcCreater: "+dcCreater);
                    //Log.i("myTag: ","slash COMMENTS: "+slashComments);
                    Log.i("myTag: ","guid: "+guid);
                    Log.i("myTag: ","description: "+desc);

                    Log.i("myTag: ","image: "+image);

                }

                Log.i("myTag: ","items in News List: "+NewsItemList.size());
                adapter = new NewsAdapter(MainActivity.this, NewsItemList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        NewsItem  news= NewsItemList.get(i);
                        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                        intent.putExtra("news_item", news);
                        startActivity(intent);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "request failed", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, "Request sent. Please Wait", Toast.LENGTH_SHORT).show();
        queue.add(stringRequest);



    }

    String removeCDate(String data){
        data = data.replace("<![CDATA[", "");
        data = data.replace("]]>", "");
        return data;
    }

}
