package com.example.assignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.icu.text.IDNA;
import android.nfc.Tag;
import android.os.Bundle;
import android.transition.Scene;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.DownloadListener;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Controller {
    private Activity myActivity;
    private WebView myWebViw;
    private String url;
    private Stack<String> History = new Stack<String>();
    private boolean screen;
    private Bundle bundle;
    private boolean save = false;
    private ArrayList<String> History1 = new ArrayList<String>();
    private HashMap<String,String>Bookmark = new HashMap<String,String>();
    private boolean bookmarkview = false;
    private String burl;





    public Controller(Activity activity){
        this.myActivity = activity;
        setUpHomeScreen();
    }

    public void setUpHomeScreen() {


        myActivity.setContentView(R.layout.activity_main);

        EditText input = (EditText) myActivity.findViewById(R.id.urlbar);


        myWebViw = (WebView) myActivity.findViewById(R.id.webview);

        if (save == true) {
            myWebViw.restoreState(bundle);
            WebSettings webSettings = myWebViw.getSettings();
            webSettings.setJavaScriptEnabled(true);

            //https://stackoverflow.com/questions/8193239/how-to-get-loaded-web-page-title-in-android-webview

            myWebViw.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                 {
                    myActivity.setTitle(view.getTitle());
                    input.setText(view.getTitle());

                    if (!History1.contains(myWebViw.getTitle())) {
                        History1.add(myWebViw.getTitle());
                    }


                }
            });
            save = false;
        } else if (bookmarkview == true) {
            myWebViw.restoreState(bundle);

            WebSettings webSettings = myWebViw.getSettings();
            webSettings.setJavaScriptEnabled(true);

            myWebViw.loadUrl(burl);

            //https://stackoverflow.com/questions/8193239/how-to-get-loaded-web-page-title-in-android-webview

            myWebViw.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    myActivity.setTitle(view.getTitle());
                    input.setText(view.getTitle());

                    if (!History1.contains(myWebViw.getTitle())) {
                        History1.add(myWebViw.getTitle());
                    }


                }
            });
            bookmarkview = false;

        } else {


            WebSettings webSettings = myWebViw.getSettings();
            webSettings.setJavaScriptEnabled(true);

            //https://stackoverflow.com/questions/8193239/how-to-get-loaded-web-page-title-in-android-webview

            myWebViw.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    myActivity.setTitle(view.getTitle());
                    input.setText(view.getTitle());
                    //      History1.add(myWebViw.getTitle());
                    if (!History1.contains(myWebViw.getTitle())) {
                        History1.add(myWebViw.getTitle());
                    }

                }
            });
            myWebViw.loadUrl("https://www.google.com");
        }


        Button btn = (Button) myActivity.findViewById(R.id.gobutton);


//        myWebViw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                txt.setText(myWebViw.getTitle());
//                input.setText(myWebViw.getUrl());
//            }
//        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String abc = String.valueOf(input.getText());

                if (!abc.contains(".com")) {
                    url = "https://www.google.com/search?q=" + input.getText();
                } else if (abc.contains("www")) {
                    url = "http://" + input.getText();
                } else {
                    url = "http://www." + input.getText();
                }


                myWebViw.loadUrl(url);


                History.push(myWebViw.getUrl());


                Log.d("Info", History.peek());


            }

        });
        Button back = (Button) myActivity.findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myWebViw.canGoBack()) {
                    myWebViw.goBack();
                } else {
                    Toast.makeText(myActivity, "cant go back", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button forward = (Button) myActivity.findViewById(R.id.forwardbutton);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myWebViw.canGoForward()) {
                    myWebViw.goForward();
                } else {
                    Toast.makeText(myActivity, "cant go forward", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button addb = (Button) myActivity.findViewById((R.id.Bookmark));
        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bookmark.put(myWebViw.getTitle(), myWebViw.getUrl());
            }
        });

        Button settings = (Button) myActivity.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingScreen();
//                bundle = new Bundle();
//                myWebViw.saveState(bundle);
//                save = true;
//
//              myActivity.setTitle("Settings");
//                myActivity.setContentView(R.layout.settings_layout);
//
//
//                screen = true;
//
//                Button hist = (Button) myActivity.findViewById(R.id.history);
//                hist.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        showHistory();
//                    }
//                });
//
//                Button bookm = (Button) myActivity.findViewById(R.id.homepage);
//                bookm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        showBookmark();
//                    }
//                });
//
//            }
//        });


            }
        });
        Button newtabb = (Button) myActivity.findViewById(R.id.newtab);
        newtabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TabLayout tb = (TabLayout) myActivity.findViewById(R.id.tablayout);
                TabLayout.Tab tb2 = tb.newTab();
                tb2.setText("newTab");

                tb.addTab(tb2);









            }
        });
    }

    public void settingScreen(){
        bundle = new Bundle();
        myWebViw.saveState(bundle);
        save = true;

        myActivity.setTitle("Settings");
        myActivity.setContentView(R.layout.settings_layout);


        screen = true;

        Button hist = (Button) myActivity.findViewById(R.id.history);
        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHistory();
            }
        });

        Button bookm = (Button) myActivity.findViewById(R.id.homepage);
        bookm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookmark();
            }
        });

    }




    public void showHistory(){
        myActivity.setTitle("History");
        myActivity.setContentView(R.layout.history_layout);
        Button clearhist = (Button)myActivity.findViewById(R.id.clear);
        clearhist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                History1.clear();
                showHistory();
            }
        });
        Button backbutt = (Button)myActivity.findViewById(R.id.back);
        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingScreen();
            }

        });
        LinearLayout L1 = (LinearLayout) myActivity.findViewById(R.id.Linear);
       // LinearLayout L2 = new LinearLayout(myActivity);
//        L1.setOrientation(LinearLayout.VERTICAL);
//        L2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
////        L2.setGravity(Gravity.CENTER);


//        WebBackForwardList wbfl = myWebViw.copyBackForwardList();







//        for(int i = 0; i<wbfl.getSize(); i++){
        int i = 1;
        for(String s : History1){
            TextView tv = new TextView(myActivity);
          //  tv.setText(wbfl.getItemAtIndex(i).getTitle());
            tv.setText(i+")"+s);
            i++;
            tv.setVisibility(View.VISIBLE);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            L1.addView(tv,(new LinearLayout.LayoutParams(0,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    0.8f)));
            L1.addView(tv);


        }
     //   L1.addView(L3);


    }

    public void showBookmark(){
        myActivity.setTitle("Bookmarks");
        myActivity.setContentView(R.layout.bookmark_layout);
        LinearLayout boo = (LinearLayout) myActivity.findViewById(R.id.Linear10);

        for(String s : Bookmark.keySet()){
            Button butt = new Button(myActivity);
            butt.setText(s);
            butt.setVisibility(View.VISIBLE);
            butt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boo.addView(butt);
            butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookmarkview = true;
                    burl = Bookmark.get(s);
                    save = false;
                    setUpHomeScreen();
                }
            });
        }

    }




    public void onBackPress(){



        if(screen == true){


          // myActivity.setContentView(R.layout.activity_main);
            screen = false;
            setUpHomeScreen();


            return;

        }

        if (myWebViw.canGoBack()){
            myWebViw.goBack();
        } else {
            Toast.makeText(myActivity,"closed", Toast.LENGTH_LONG).show();
            myActivity.finish();
        }


    }

}
