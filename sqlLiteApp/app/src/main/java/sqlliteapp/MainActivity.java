package moji.physics.sqlliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

//import java.util.List;

public class MainActivity extends AppCompatActivity {

    //references to buttons and other controls
    Button btn_add, btn_viewAll, btn_sumAll;
    EditText et_name, et_age, et_price, et_total;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw_activeCustomer;
    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    int sub_price=0;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Shopping List");

        btn_add=findViewById(R.id.addBtn);
        btn_viewAll=findViewById(R.id.viewAllBtn);
        btn_sumAll=findViewById(R.id.btn_sumAll);

        et_age=findViewById(R.id.ageText);//numberOfItems
        et_name=findViewById(R.id.nameText);//Product Name
        et_price=findViewById(R.id.priceText);//2021-09-13
        et_total=findViewById(R.id.totalText);//2021-09-13
        sw_activeCustomer=findViewById(R.id.activeSwitch);
        lv_customerList=findViewById(R.id.customerListView);
        //displays all the users from DB
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        ShowCustomerOnListView(dataBaseHelper);
        //button listeners
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adding new prices
                sub_price=Integer.parseInt(et_price.getText().toString()) * Integer.parseInt(et_age.getText().toString());//2021-09-13
                //et_total.setText(sub_price+"");//2021-09-13

                CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1,et_name.getText().toString(),
                            Integer.parseInt(et_age.getText().toString()),
                            Integer.parseInt(et_price.getText().toString()),
                            sub_price,sw_activeCustomer.isChecked());
                    //customerModel = new CustomerModel(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()),sub_price,sw_activeCustomer.isChecked());

                    //show a small message at the bottom of the screen
                    Toast.makeText(MainActivity.this,customerModel.toString(),Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Error creating customer.",Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1,"error",0,0,0,false);
                }

                DataBaseHelper dataBaseHelper=new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this, "Success: "+success,Toast.LENGTH_SHORT).show();
                //after adding a new user, displays all the members of DB
                ShowCustomerOnListView(dataBaseHelper);
                /*works for new items only not all
                unit_price=dataBaseHelper.sumPrices(customerModel);
                et_total.setText(unit_price+"");*/

            }
        });
        btn_viewAll.setOnClickListener((v) -> {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

            ShowCustomerOnListView(dataBaseHelper);

            //Toast.makeText(MainActivity.this,everyone.toString(),Toast.LENGTH_SHORT).show();
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                //when deleting an item, should update total price. 2021-09-13
                ShowCustomerOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this,"Deleted "+clickedCustomer.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        btn_sumAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update Total text
                updateTotal(dataBaseHelper);

            }
        });
    }

    private void ShowCustomerOnListView(DataBaseHelper dataBaseHelper2) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        lv_customerList.setAdapter(customerArrayAdapter);
    }

    private void updateTotal(DataBaseHelper myDBHelper){
        /*sub_price=Integer.parseInt(et_price.getText().toString()) * Integer.parseInt(et_age.getText().toString());//2021-09-13
        CustomerModel customerModel;
        customerModel = new CustomerModel(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()),sub_price,sw_activeCustomer.isChecked());
        int tot_price=myDBHelper.sumPrices(customerModel);*/
        CustomerModel customerModel;
        customerModel=new CustomerModel(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()),
                Integer.parseInt(et_price.getText().toString()),sub_price,sw_activeCustomer.isChecked());
        Cursor aCursor=myDBHelper.sumAllPrices(customerModel);
        @SuppressLint("Range") int tot_price= aCursor.getInt(aCursor.getColumnIndex("myTotal"));
        et_total.setText(tot_price+"");
    }
}