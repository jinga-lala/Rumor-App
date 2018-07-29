package com.hfad.layoutprac;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import com.hfad.layoutprac.Database.DatabaseHandler;


public class MainActivity extends AppCompatActivity {
    ImageView newStack,Share,Delete,Favroite,cardImg,Profil;
    TextView MyFavroite;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> array;
    LinearLayout linearLayout;
    private ArrayList<Info> data;
    String userDataString = "";
    String username = "";
    String useremail = "";
    private SwipeFlingAdapterView flingContainer;
    DatabaseHandler db;
    SharedPreferences.Editor edit;
    SharedPreferences data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHandler(this);

        linearLayout=(LinearLayout)findViewById(R.id.linearpro);
        MyFavroite=(TextView)findViewById(R.id.myfav);
        Profil=(ImageView)findViewById(R.id.profile);
        newStack=(ImageView)findViewById(R.id.newstack);
        Share=(ImageView)findViewById(R.id.share);
        Delete=(ImageView)findViewById(R.id.del);
        Favroite=(ImageView)findViewById(R.id.fav);
        cardImg=(ImageView)findViewById(R.id.cardImage);
        data1 = getSharedPreferences("rumour", MODE_PRIVATE);
        edit = data1.edit();
        userDataString = data1.getString("userphone", "");
        Log.d("MainActivity","the value of phone data is "+userDataString);
        username = data1.getString("user", "");
        Log.d("MainActivity","the value of name data is "+username);
        useremail = data1.getString("email", "");
        Log.d("MainActivity","the value of email data is "+useremail);
        newStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Uploadimg.class);
                startActivity(i);
            }
        });

        Log.d("MainActivity","Stack007 Start Get data");
        data=new ArrayList<>();
        data=db.getInfoListMy();
        Log.d("MainActivity","Stack007 Start Get data finish");
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Log.d("MainActivity","Stack007 Start Data Array");
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        array = new ArrayList<>();
        for(Info i:data) {
            array.add(new Data(i.getPathImagename(), i.getId()));
        }
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (array.size()==0){
                    Toast.makeText(MainActivity.this,"data is null",Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity","data null");
                }
                else {
                    array.get(0).getDescription();
                    db.deleteASingleRow(array.get(0).getDescription().toString());
                    array.remove(0);
                    Log.d("#000", "Delete Success");
                    myAppAdapter.notifyDataSetChanged();
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( (data1.getString("userType", "")).equals("profile")){

                    edit.putString("userType","anym");
                    edit.commit();
                    // Profil.setImageResource(R.drawable.capture);
                    Log.d("MainActivity","Stack007 data was in profile now sent to anym");
                }
                else if( (data1.getString("userType", "")).equals("anym")){

                    edit.putString("userType","profile");
                    edit.commit();
                    //  Profil.setImageResource(R.drawable.fav);
                    Log.d("MainActivity","Stack007 data was in anym now sent to profile");;
                }else{
                    Log.d("MainActivity","Stack007 data was inladt");;
                    edit.putString("userType","profile");
                    edit.commit();
                }
            }
        });
        MyFavroite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Myfav.class);
                startActivity(i);
            }
        });
        Favroite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (array.size()==0){
                    Toast.makeText(MainActivity.this,"data is null",Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity","data null");
                }
                else {
                    array.get(0).getDescription();
                    db.updatedata(array.get(0).getDescription().toString());
                    Log.d("#000", "update Success");
                    array.remove(0);
                    myAppAdapter.notifyDataSetChanged();
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });


        myAppAdapter = new MyAppAdapter(array, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                array.get(0).getDescription();
                db.updatedata(array.get(0).getDescription().toString());
                Log.d("#000","update Success");
                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                array.get(0).getDescription();
                db.deleteASingleRow(array.get(0).getDescription().toString());
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


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
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

            Glide.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }


}