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


public class ProductActivity extends ActionBarActivity {
    public TextView selectionMarket; //���������� ��� ��������� ����� ������ ��������� ���������
    public ListView listViewMarket; //���������� ��� ����� ���������
    public TextView selectionProduct; //���������� ��� ��������� ����� ������ ��������� ���������
    public ListView listViewProduct; //���������� ��� ����� ���������
    public String typeProduct; //��������� ����� ���� ��������� (����/�����/etc)
    public ArrayList<String> listProducts = new ArrayList(); //����� ������ ���������
    public ArrayList<String> vegetablesListProducts = new ArrayList(); //
    public ArrayList<String> fruitsListProducts = new ArrayList(); //
    public ArrayList<String> groatsListProducts = new ArrayList(); //
    public ArrayList<String> meatListProducts = new ArrayList(); //
    static final private int ACTIVITY_NUMBER = 0;
    public String N;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // �������� ��������� �������� ListView
        listViewProduct = (ListView)findViewById(R.id.listViewProduct);
        //�������� �������� ��������� ����� ������ ��������� ���������
        selectionProduct = (TextView) findViewById(R.id.textViewProductListTest);
        //�������� �������� ��������� ����� ������ ��������� ���������
        selectionMarket = (TextView) findViewById(R.id.textViewMarketListTest);
        // ���������� ������ ���� String
        final String[] productName = getResources().getStringArray(R.array.productNames);
        // ���������� ������� ������
        ArrayAdapter<String> adapterProduct = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, productName);
        listViewProduct.setAdapter(adapterProduct); //������������� ������� � ������ ���������

        //�������� �� ������������ ������ ��������� ���������
        String listMarket = "������ ��������� ��������� �� ������. ��� �������";
        listMarket = getIntent().getExtras().getString("listMarkets");
        //����������� �� � �������� �����
        selectionMarket.setText(listMarket);

        //������ ��������� ��� ������ ���������
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
        //        selectionProduct.setText(""); //��������� ����� ������ ��������� ���������

                //������ ������ ��������, � ������� ����� �������� ������
                SparseBooleanArray chosenProduct = ((ListView) parent).getCheckedItemPositions();
                //���������� ���� ������
            //    for (int i = 0; i < chosenProduct.size(); i++) {
                    // ���� ������������ ������ ����� ������, �� ������� ��� � TextView.
             //       if (chosenProduct.valueAt(i)) {
                        //������� ���������� �������� � �����
              //          selectionProduct.append(productName[chosenProduct.keyAt(i)] + " ");
             //       }
            //    }

                //�������������� �������� ������ ���������
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString(); // �������� ����� �������� ��������
                if(strText.equalsIgnoreCase(getResources().getString(R.string.Vegetables))) {
                    typeProduct = "vegetables";
                    N = "1";
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.Fruits))) {
                    typeProduct = "fruits";
                    N = "2";
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.Groats))) {
                    typeProduct = "groats";
                    N = "3";
                }else if (strText.equalsIgnoreCase(getResources().getString(R.string.Meat))) {
                    typeProduct = "meat";
                    N = "4";
                }
                else {
                    typeProduct = "no products";
                }
                // ��������� ���������� ������ ���������� ���������
                Intent intentSubProduct = new Intent(ProductActivity.this, SubProductActivity.class);
                // � ���� typeProduct ������ ��������� �����, �� ������� ����� ����� ��� ���������
                intentSubProduct.putExtra("typeProduct", typeProduct);
                startActivityForResult(intentSubProduct, ACTIVITY_NUMBER);

            }
        });


    }

    //�������, ������������� ��� �������� �� �������� ������ ���������
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // TextView infoTextView = (TextView) findViewById(R.id.textViewProductListTest);

        if (requestCode == ACTIVITY_NUMBER) {
            if (resultCode == RESULT_OK) {
                String productName = data.getStringExtra(SubProductActivity.PRODECTS_LIST);
                selectionProduct.setText(productName);




            }else {
                selectionProduct.setText("����� �����-�� ���������"); // ����� �����-�� ���������
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product, menu);
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
    public void onClickNext2(View view) {
        Intent intent = new Intent(ProductActivity.this, TableActivity.class);
        startActivity(intent);
    }

    //��������� �������� � ������������ � ������ ���������
    public void onClickBack2(View view) {
        finish();
    }
}
