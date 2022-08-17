package moji.physics.example0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShopListAct extends AppCompatActivity {

    ListView myListView;
    String[] items;
    String[] prices;
    String[] descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.myListView);
        items =res.getStringArray(R.array.shoplistItems);
        //displays the output from string-array
        //myListView.setAdapter(new ArrayAdapter<String>(this,R.layout.my_listview_detail,items));
        prices=res.getStringArray(R.array.shoplistPrices);
        descriptions=res.getStringArray(R.array.descrItems);

        ItemAdapter itemAdapter= new ItemAdapter(this,items,prices,descriptions);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showPictureAct = new Intent(getApplicationContext(),showImgAct.class);
                showPictureAct.putExtra("org.moji.physics.example0.ITEM_INDEX",i);
                startActivity(showPictureAct);
            }
        });
    }
}