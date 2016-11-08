package com.egco28.a13262;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected List<Fortune> data = new ArrayList<>();
    private FortuneDataSource fortuneDataSource;
    private ArrayAdapter fortuneArrayAdapter;
//    String[] fortuneData = {"You can do it!", "You will get A", "You're Lucky", "Don't cry, Life is pain", "Today is not your day","Don't Panic"};
//    String dateData = "30 Oct 2016";
//    String timeData = "9:21";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      this code is for changing action bar color :
//        getSupportActionBar().setBackgroundDrawable(
//                new ColorDrawable(Color.parseColor("#e91e63")));

        //these codes based on project"list04_2"

        //this code for testing listview
//        data.add(new Fortune(fortuneData[0],dateData,timeData,0));
//        data.add(new Fortune(fortuneData[1],dateData,timeData,1));
//        data.add(new Fortune(fortuneData[2],dateData,timeData,2));
//        data.add(new Fortune(fortuneData[3],dateData,timeData,3));
//        data.add(new Fortune(fortuneData[4],dateData,timeData,4));
//        data.add(new Fortune(fortuneData[5],dateData,timeData,5));

        fortuneDataSource = new FortuneDataSource(this);
        fortuneDataSource.open();

        data = fortuneDataSource.getAllFortunes();
        fortuneArrayAdapter = new FortuneArrayAdapter(this, data);
        ListView listView = (ListView)findViewById(R.id.resultsList);

        listView.setAdapter(fortuneArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long l) {

                //order to delete from Database here
                final Fortune deleteData = (Fortune)fortuneArrayAdapter.getItem(position);
                fortuneDataSource.deleteResult(deleteData);

                view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable(){

                @Override
                public void run(){

                //order to delete from ListView here
                fortuneArrayAdapter.remove(deleteData);
                fortuneArrayAdapter.notifyDataSetChanged();

                view.setAlpha(1);
                }
                });
            }
        });

    }
    //this code is for add button on action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this,NewCookies.class);
        startActivityForResult(intent, NewCookies.DETAIL_REQ_CODE); //for going to new Activity(new page)
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//if program has set by setResult this function need to be.
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NewCookies.DETAIL_REQ_CODE){
            if(resultCode == NewCookies.RESULT_OK){
                String dataResult = data.getStringExtra(NewCookies.Fortune_Result);
                String dataDateTime = data.getStringExtra(NewCookies.Fortune_DateTime);
                int dataPicIndex = data.getIntExtra(NewCookies.Fortune_PicIndex,0);

                Fortune dataTotal = fortuneDataSource.createResult(dataResult, dataDateTime, dataPicIndex);
                fortuneArrayAdapter.add(dataTotal);
                fortuneArrayAdapter.notifyDataSetChanged(); //refresh listView to show updated data


            }
        }
    }
}
