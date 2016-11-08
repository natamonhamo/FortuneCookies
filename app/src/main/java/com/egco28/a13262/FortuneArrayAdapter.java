package com.egco28.a13262;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Natamon Tangmo on 30-Oct-16.
 */
public class FortuneArrayAdapter extends ArrayAdapter<Fortune> { //this class is one of item from listview
    Context context;
    List<Fortune> fortuneList;
    public FortuneArrayAdapter(Context context, List<Fortune> fortuneList){
        super(context, 0, fortuneList);
        this.context = context;
        this.fortuneList = fortuneList;
    }

    @Override //click right mouse -> generate -> override -> getView
    public View getView(int position, View convertView, ViewGroup parent) {

        Fortune fortune = fortuneList.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fortune_item, null);

        TextView restxt = (TextView)view.findViewById(R.id.resultText);
        restxt.setText(fortune.getResults());
        if(fortune.getPicIndex() >= 3){
            restxt.setTextColor(Color.parseColor("#FF9800")); //bad meaning
        }
        else{
            restxt.setTextColor(Color.parseColor("#2196F3")); //good meaning
        }

        TextView datetxt = (TextView)view.findViewById(R.id.datetimeText);
        String datetimetxt = fortune.getDateTime();
        datetxt.setText("Date: " +  datetimetxt);

        ImageView resimg = (ImageView)view.findViewById(R.id.resultImage);
        int picname = context.getResources().getIdentifier("pic"+fortune.getPicIndex(), "drawable" , context.getPackageName());
        resimg.setImageResource(picname);

        return view;
    }
}