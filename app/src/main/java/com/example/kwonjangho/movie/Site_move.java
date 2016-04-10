package com.example.kwonjangho.movie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class Site_move extends AppCompatActivity {

    String data;
    TextView textView5;

    EditText edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_move);

        edit = (EditText) findViewById(R.id.edit);

        textView5 = (TextView) findViewById(R.id.textView5);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                data= pase(); //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        textView5.setText(data);  //TextView에 문자열  data 출력
                    }
                });

            }
        }).start();

    }

    private String pase() /*영화 이름*/ {

        StringBuffer buffer = new StringBuffer();
        try {

            int i = 0;

            Document doc = Jsoup.connect("http://movie.naver.com/movie/point/af/list.nhn").get();
            //Elements elements2 = naverNews.select("td.reserve_cnt > em");
            Elements elements2 = doc.select("td.title > a");
            Elements elements1 = doc.select("td.title");

            String str = elements1.text();


            for (Element element : elements2) {


              str = str.replace(element.text(),element.text()+"\n");
                str = str.replaceAll("\n\n", " \n");

            }

            str = str.replaceAll("신고","\n");

            buffer.append(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }



    public void onClick_0(View v){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onClick_01(View v){
        Intent intent = new Intent(getApplicationContext(),Site_move.class);
        startActivity(intent);
        finish();
    }

    public void onClick_02(View v){
        Intent intent = new Intent(getApplicationContext(),category.class);
        startActivity(intent);
        finish();
    }
    public void onClick_03(View v){
        Intent intent = new Intent(getApplicationContext(),
                reserve.class);
        startActivity(intent);
        finish();
    }
    public void onClick_04(View v){
        Intent intent = new Intent(getApplicationContext(),
                grade.class);
        startActivity(intent);
        finish();
    }
    public void onClick_cgv(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cgv.co.kr"));
        startActivity(intent);
    }
    public void onClick_megabox(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.megabox.co.kr"));
        startActivity(intent);
    }
    public void onClick_lotte(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.lottecinema.co.kr"));
        startActivity(intent);
    }
}
