package com.hfad.layoutprac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import com.hfad.layoutprac.Database.DatabaseHandler;


public class Myfav extends AppCompatActivity {
    ImageView newStack,Share,Delete,Favroite,cardImg,MyFavroite,stack;
    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Info> array;
    // private ArrayList<Info> data;
    private SwipeFlingAdapterView flingContainer;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfav);
        stack=(ImageView)findViewById(R.id.mystack);
        stack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Myfav.this,MainActivity.class);
                startActivity(i);

            }
        });
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        db=new DatabaseHandler(Myfav.this);
        array = new ArrayList<>();
        array=db.MyFavroites();

        /*for(Info i:data) {
            array.add(new Data(i.getPathImagename(), i.getId()));
        }*/
        myAppAdapter = new MyAppAdapter(array, Myfav.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                array.get(0).getId();
                // db.updatedata(array.get(0).getId().toString());

                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                array.get(0).getId();
                db.deleteASingleRow(array.get(0).getId().toString());
                array.remove(0);
                Log.d("#000","Delete Success");
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);


                myAppAdapter.notifyDataSetChanged();
            }
        });
    }




    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Info> parkingList;
        public Context context;

        private MyAppAdapter(List<Info> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.content_main2, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                // viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //    viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            Glide.with(Myfav.this).load(parkingList.get(position).getPathImagename()).into(viewHolder.cardImage);

            return rowView;
        }
    }

}
