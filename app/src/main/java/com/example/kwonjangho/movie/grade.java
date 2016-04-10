package com.example.kwonjangho.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class grade extends AppCompatActivity {

    TextView text_view;
    TextView text_view_1;

    String data;
    String data_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade);

        text_view_1 = (TextView)findViewById(R.id.text_view_1);
        text_view = (TextView) findViewById(R.id.text_view);

                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        data = pase(); //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                        data_1 = pase_1();

                        //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                        //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                text_view.setText(data);  //TextView에 문자열  data 출력
                                text_view_1.setText(data_1);
                            }
                        });
                    }
                }).start();
        }

        private String pase() /*영화 이름*/ {
            StringBuffer buffer = new StringBuffer();
            try {
                Document doc = Jsoup.connect("http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=cur&date=20151219").get();
                //Elements elements2 = naverNews.select("td.reserve_cnt > em");
                Elements elements2 = doc.select("div.tit5 > a");
                for (Element element : elements2) {
                    buffer.append("\n\n    "+element.text());
                    buffer.append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

       private String pase_1()  {//예매
        StringBuffer buffer = new StringBuffer();
            try {
                Document doc = Jsoup.connect("http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=cur&date=20151219").get();
                //Elements elements2 = naverNews.select("td.reserve_cnt > em");
                Elements elements1 = doc.select("td.point");
                for (Element element : elements1) {
                    buffer.append("\n\n 평점  :  "+element.text()+"\t\t");
                    buffer.append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer.toString();
    }




    public void onClick_0(View v) {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick_01(View v) {
        Intent intent = new Intent(getApplicationContext(), Site_move.class);
        startActivity(intent);
        finish();
    }

    public void onClick_02(View v) {
        Intent intent = new Intent(getApplicationContext(),
                category.class);
        startActivity(intent);
        finish();
    }

    public void onClick_03(View v){
         Intent intent = new Intent(getApplicationContext(),
                 reserve.class);
         startActivity(intent);
         finish();
     }
    public void onClick_04(View v) {
        Intent intent = new Intent(getApplicationContext(),
                grade.class);
        startActivity(intent);
        finish();
    }
}//MainActivity class..

