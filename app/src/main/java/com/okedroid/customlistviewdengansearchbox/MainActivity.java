package com.okedroid.customlistviewdengansearchbox;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    //Aray list akan di simpan di dalam searchResults
    ArrayList<HashMap<String, Object>> searchResults;

    //ArrayList akan menyimpan data asli dari originalValues
    ArrayList<HashMap<String, Object>> originalValues;
    LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final EditText kotakpencari=(EditText) findViewById(R.id.kotakpencari);
        ListView playersListView=(ListView) findViewById(android.R.id.list);

        //mengambil LayoutInflater untuk inflating thcustomView
        //disini akan menggunakan custom adapter
        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //disini data aray akan di deklarasikan
        //dan akan disimpan ke dalam Arraylist
        //tipe data string untuk textview integer untuk gambar gambar
        String namamakanan[]={"Es Blewah","Kurma Madu","Es Campur","Kolak Banana", "Es Cendol"};
        String detailmakanans[]={"Es Blewah Segar","Kurma Madu Alami","Es Campur Sari","Kolak Banana Wan","Es Cedol Ok"};
        Integer[] gambars ={R.drawable.blewah,R.drawable.kurma,R.drawable.escampur,R.drawable.kolak,R.drawable.cendol};

        originalValues=new ArrayList<HashMap<String,Object>>();

        //hasmap akan menyimpan data sementara dalam listview
        HashMap<String , Object> temp;

        //jumlah baris dalam ListView
        int noOfPlayers=namamakanan.length;

        //pengulangan dalam Arraylist
        for(int i=0;i<noOfPlayers;i++)
        {
            temp=new HashMap<String, Object>();

            temp.put("namamakanan", namamakanan[i]);
            temp.put("detailmakanan", detailmakanans[i]);
            temp.put("gambar", gambars[i]);

            //menambah kan baris ke dalam  ArrayList
            originalValues.add(temp);
        }
        //searchResults sama dengan OriginalValues
        searchResults=new ArrayList<HashMap<String,Object>>(originalValues);

        //membuat adapter
        //first param-the context
        //second param-the id of the layout file
        //you will be using to fill a row
        //third param-the set of values that
        //will populate the ListView
        final CustomAdapter adapter=new CustomAdapter(this, R.layout.daftar_makanan,searchResults);

        //menset adapter di dalam listview
        playersListView.setAdapter(adapter);
        kotakpencari.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //mengambil text di dalam  EditText
                String searchString=kotakpencari.getText().toString();
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<originalValues.size();i++)
                {
                    String playerName=originalValues.get(i).get("namamakanan").toString();
                    if(textLength<=playerName.length()){
                        //membandingkan data String didalam EditText dengan namamakanan  di dalam ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults.add(originalValues.get(i));
                    }}
                adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            public void afterTextChanged(Editable s) {}
        });
    }

    //mendefinisikan custom adapter
    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>>
    {
        public CustomAdapter(Context context, int textViewResourceId,
                             ArrayList<HashMap<String, Object>> Strings) {


            super(context, textViewResourceId, Strings);
        }

        //class untuk menyimpan baris konten (cacheview) di listview
        private class ViewHolder
        {
            ImageView gambar;
            TextView namamakanan,detailmakanan;
        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.daftar_makanan, null);
                viewHolder=new ViewHolder();

                //isi konten (cache the views)
                viewHolder.gambar=(ImageView) convertView.findViewById(R.id.gambar);
                viewHolder.namamakanan=(TextView) convertView.findViewById(R.id.namamakanan);
                viewHolder.detailmakanan=(TextView) convertView.findViewById(R.id.detailmakanan);

                //menghubungkan cached views ke dalam convertview
                convertView.setTag(viewHolder);
            }
            else
                viewHolder=(ViewHolder) convertView.getTag();
            int gambarId=(Integer) searchResults.get(position).get("gambar");

            //menset data untuk ditampilkan
            viewHolder.gambar.setImageDrawable(getResources().getDrawable(gambarId));
            viewHolder.namamakanan.setText(searchResults.get(position).get("namamakanan").toString());
            viewHolder.detailmakanan.setText(searchResults.get(position).get("detailmakanan").toString());
            //mengembalikan view untuk ditampilkan
            return convertView;
        }
    }
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);//menggunakan method onliistitemclick dan mencarinya
        //berdasarkan posisi
        String str = searchResults.get(position).get("namamakanan").toString();
        switch (str) {
            case "Es Blewah":
                Intent a = new Intent(MainActivity.this, AActivity.class);
                startActivity(a);
                break;

            case "Kurma Madu":
                Intent b = new Intent(MainActivity.this, BActivity.class);
                startActivity(b);
                break;

            case "Es Campur":
                Intent c = new Intent(MainActivity.this, CActivity.class);
                startActivity(c);
                break;
        }
    }
}