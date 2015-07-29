package com.tsubaku.savingsinshops;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class SubProductActivity extends ActionBarActivity {
    public TextView selectionProduct; //���������� ��� ��������� ����� ������ ��������� ���������
    public ListView listViewVegetables; //���������� ��� ������ ������
    public ListView listViewProducts; //���������� ��� ������ ���������
    public HashMap<String, String> mapProduct = new HashMap<String, String>();
    public HashMap<String, String> generalMapProduct = new HashMap<String, String>();   //����� ������ ��������� ���������
    public ArrayList<String> listChangeMarket;  //������ ��������� ���������
    String productGroup; //������ ��� V - ������ ��������� � HashMap
    SparseBooleanArray chosenProducts;
    String[] Products;
    public final static String PRODECTS_LIST = "com.tsubaku.savingsinshops.Products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_product);


        //�������� �������� ��������� ����� ������ ��������� ���������
        selectionProduct = (TextView) findViewById(R.id.textViewProductListTest);
        selectionProduct.setText("");   //����� � ��������

        //�������� �� ��������������� ����� ���� ���������
        String typeSubProduct = "type";
        typeSubProduct = getIntent().getExtras().getString("typeProduct");
        //����������� �� � �������� �����
        //selectionProduct.setText(typeSubProduct);

        //�������� ����� ������ ��������� ���������
        Intent intentProduct = getIntent();
        generalMapProduct = (HashMap<String, String>)intentProduct.getSerializableExtra("generalMapProduct");

        //�������� ������ ��������� ���������
        //Intent intentMarket = getIntent();
        //listChangeMarket = (ArrayList<String>)intentMarket.getSerializableExtra("putChangeMarketList");

        // �������� ��������� �������� ListView ��� ������ ������/�������/etc
        listViewProducts = (ListView)findViewById(R.id.listViewSubProducts);

        //�������, ����� ��� ��������� ������ � ��������� ������� ��� ����

        if(typeSubProduct.equalsIgnoreCase("vegetables")) {
            //selectionProduct.append(typeSubProduct + " 1");
            Products = getResources().getStringArray(R.array.vegetablesNames);
            productGroup = "vegetables";


        } else if (typeSubProduct.equalsIgnoreCase("fruits")) {
            //selectionProduct.append(typeSubProduct + " 2");
            Products = getResources().getStringArray(R.array.fruitsNames);
            productGroup = "fruits";

        } else if (typeSubProduct.equalsIgnoreCase("groats")) {
            //selectionProduct.append(typeSubProduct + " 3");
            Products = getResources().getStringArray(R.array.groatsNames);
            productGroup = "groats";

        }else if (typeSubProduct.equalsIgnoreCase("meat")) {
            //selectionProduct.append(typeSubProduct + " 4");
            Products = getResources().getStringArray(R.array.meatNames);
            productGroup = "meat";

        }
        else {
            //selectionProduct.append(typeSubProduct + " 0");
            Products = getResources().getStringArray(R.array.meatNames);
        }
        // ���������� ������� ������
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, Products);
        listViewProducts.setAdapter(adapter3); //������������� ������� � ������ ���������

        //����������� ������� �� ���������, � ����������� �� ����� ���������� ������
        ///listViewProducts.performItemClick(listViewProducts.getAdapter().
        ///        getView(0, null, null), 0, listViewProducts.getAdapter().
        ///        getItemId(0));

        if (generalMapProduct.isEmpty()){
            //���� ������� ���� ������, �� ������ ������
        }else{
            for(Iterator<HashMap.Entry<String, String>> it = generalMapProduct.entrySet().iterator(); it.hasNext(); ) {
                HashMap.Entry<String, String> entry = it.next();
                if(entry.getValue().equals(productGroup)) { //���� �������, �� ���-�� ��� ���� ������ �������. �����?
                String keyProduct = entry.getKey();       //�������� ��� ���������� ����� ��������
                    for (int i = 0; i < adapter3.getCount(); i++) {
                        if (adapter3.getItem(i).equals(keyProduct)) {
                            //��������� �� � ������ ��������� ���������
                            listViewProducts.performItemClick(listViewProducts.getAdapter().
                                getView(i, null, null), i, listViewProducts.getAdapter().
                                getItemId(i)); //���� ������ ������� �� ����� ��������� ��������
                        } else {
                            Toast.makeText(getApplicationContext(), productGroup, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    //������
                }
            }
        }


        //������ ��������� ��� ������ ���������
        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View itemClicked, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
                selectionProduct.setText("");//�������� ������ ����� ������ ���������

                //������ ������ ��������, � ������� ����� �������� ������
                chosenProducts = ((ListView) parent).getCheckedItemPositions();
                //���������� ���� ������
                for (int i = 0; i < chosenProducts.size(); i++) {
                    // ���� ������������ ������ ����� �� ������, �� ������� ��� � TextView.
                    if (chosenProducts.valueAt(i)) {
                        //������� ���������� �������� � �����
                        mapProduct.put(Products[chosenProducts.keyAt(i)], productGroup);
                        for (String key : mapProduct.keySet()) {
                        selectionProduct.append("  " + key + "  ");
                        }

                    }
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //��������� � �������� � ��������
    public void onClickOk3(View view) {

        //���������� ���� ������ ��������� ����� ������
        mapProduct.clear();
        chosenProducts = listViewProducts.getCheckedItemPositions();
        for (int i = 0; i < chosenProducts.size(); i++) {
            // ���� ������������ ������ ����� �� ������, �� ������� ��� � TextView.
            if (chosenProducts.valueAt(i)) {
                //��������� �� � ������ ��������� ���������
                mapProduct.put(Products[chosenProducts.keyAt(i)], productGroup);
            }
        }

        //������� ������ ��������� ��������� � ���������������
        Intent answerSubProductIntent = new Intent();
        answerSubProductIntent.putExtra("PRODECTS_LIST", mapProduct);           //��������� ��������� ��������
        //answerSubProductIntent.putExtra("putChangeMarketList", listChangeMarket);//��������� ��������� ��������
        setResult(RESULT_OK, answerSubProductIntent);
        finish();
    }

    //��������� �������� � ������������ � ������ ���������
    public void onClickCancel3(View view) {
        finish();
    }

}
