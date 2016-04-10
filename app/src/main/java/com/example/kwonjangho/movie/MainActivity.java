package com.example.kwonjangho.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {



    EditText edit;
    TextView main_textview;

    XmlPullParser xpp;
    String key="df02c393dc1461bcfa14ca56a14fece0"; //Naver 개발자센터 검색 키

    String data_1;
    String data;


    TextView textView4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_textview= (TextView)findViewById(R.id.main_textview);
        edit= (EditText)findViewById(R.id.edit);

        textView4 = (TextView) findViewById(R.id.textView4);

        //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                data = pase(); //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        textView4.setText(data);  //TextView에 문자열  data 출력
                    }
                });
            }
        }).start();
    }



    private String pase() /*영화 이름*/ {
        StringBuffer buffer = new StringBuffer();
        try {
            int i = 1,b=1;
            Document doc = Jsoup.connect("http://movie.naver.com/movie/running/current.nhn").get();
            //Elements elements2 = naverNews.select("td.reserve_cnt > em");
            Elements elements2 = doc.select("dt.tit > a");
            for (Element element : elements2) {
                if(b <= 20){
                    b++;
                }
                else
                    break;
                buffer.append("\n\n  "+i+". "+element.text());
                buffer.append("\n");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public void onClick_0(View v){
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onClick_01(View v){
        Intent intent = new Intent(getApplicationContext(),Site_move.class);
        startActivity(intent);
        finish();
    }
    public void onClick_02(View v){
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
    public void onClick_04(View v){
        Intent intent = new Intent(getApplicationContext(),
                grade.class);
        startActivity(intent);
        finish();
    }
    public void category_01(View v){
        Intent intent = new Intent(getApplicationContext(),
                category.class);
        startActivity(intent);
        finish();
    }

    public void main_search(View v){ //버튼 onClick

        switch( v.getId() ){
            case R.id.main_button:   // 버튼 id

                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        data= getXmlData(); //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                        //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                        //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                main_textview.setText(data);  //TextView에 문자열  data 출력
                            }
                        });

                    }
                }).start();

                break;
        }

    }//mOnClick method..

    int i = 0;
    //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    String getXmlData() {

        StringBuffer buffer = new StringBuffer();

        Editable str = edit.getText();  //EditText에 작성된 Text얻어오기
        str = edit.getText();

        String location = URLEncoder.encode(str.toString()); //한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..

        String queryUrl = "http://openapi.naver.com/search"   //요청 URL
                + "?key=" + key                        //key 값
                + "&target=movie"                     //검색서비스 api명세
                + "&query="+location                            //영화검색 요청값
                + "&display=1"                      //검색 출력 건수  10~100
                + "&start=1";                        //검색 시작 위치  1~1000

        try {
            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:

                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if (tag.equals("title")) {
                            buffer.append("영화 제목 : ");
                            xpp.next();
                            String html = xpp.getText();
                            html = html.replaceAll("\\<.*?>", "");

                            buffer.append(html); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                        } /*else if (tag.equals("image")) {
                            buffer.append("영화 이미지 : ");
                            xpp.next();
                            buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추
                            buffer.append("\n");          //줄바꿈 문자 추가
                        } */else if (tag.equals("pubDate")) {
                            buffer.append("영화 제작년도 :");
                            xpp.next();
                            buffer.append(xpp.getText()); //telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                        } else if (tag.equals("director")) {
                            buffer.append("영화의 감독 :");
                            xpp.next();
                            buffer.append(xpp.getText()); //address 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                        } else if ( tag.equals("actor")) {
                            buffer.append("영화 출연 배우 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("userRating")) {
                            buffer.append("영화의 평점 :");
                            xpp.next();
                            buffer.append(xpp.getText()); //mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");          //줄바꿈 문자 추가
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기

                        if (tag.equals("item")) buffer.append("\n"); // 첫번째 검색결과종료..줄바꿈

                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        buffer.append("\n----검색 끝----");
        return buffer.toString(); //StringBuffer 문자열 객체 반환

    }//getXmlData method....
}//MainActivity class..

