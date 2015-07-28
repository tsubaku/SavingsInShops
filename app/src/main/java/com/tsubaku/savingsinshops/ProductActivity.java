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

import java.util.HashMap;
import java.util.Iterator;


public class ProductActivity extends ActionBarActivity {
    public TextView selectionMarket;    //���������� ��� ��������� ����� ������ ��������� ���������
    public ListView listViewMarket;     //���������� ��� ����� ���������
    public TextView selectionProduct;   //���������� ��� ��������� ����� ������ ��������� ���������
    public ListView listViewProduct;    //���������� ��� ����� ���������
    public String typeProduct;          //��������� ����� ���� ��������� (����/�����/etc)
    public HashMap<String, String> generalMapProduct = new HashMap<String, String>();   //����� ������ ��������� ���������
    public HashMap<String, String> mapProduct = new HashMap<String, String>();          //������ ����� ��������� ���������
    static final private int NUMBER_SUB_PRODUCT_ACTIVITY = 0;                           //����� �������� ������ ���������

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listViewProduct = (ListView)findViewById(R.id.listViewProduct);             //������� ���������
        selectionProduct = (TextView) findViewById(R.id.textViewProductListTest);   //������ ��������� ���������
        selectionMarket = (TextView) findViewById(R.id.textViewMarketListTest);     //������ ��������� ���������
        final String[] productName = getResources().getStringArray(R.array.productNames);//������ ��� ����� ����� ���������
        // ���������� ������� ������
        ArrayAdapter<String> adapterProduct = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productName);
        listViewProduct.setAdapter(adapterProduct);     //������������� ������� � ������ ���������

        //�������� �� ������������ ������ ��������� ���������
        String listMarket = "������ ��������� ��������� �� ������. ��� �������";
        listMarket = getIntent().getExtras().getString("listMarkets");
        selectionMarket.setText(listMarket);    //���������� ������ ��������� ���������

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
                    //N = "1";
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.Fruits))) {
                    typeProduct = "fruits";
                    //N = "2";
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.Groats))) {
                    typeProduct = "groats";
                    //N = "3";
                }else if (strText.equalsIgnoreCase(getResources().getString(R.string.Meat))) {
                    typeProduct = "meat";
                    //N = "4";
                }
                else {
                    typeProduct = "no products";
                }
                // ��������� ���������� ������ ���������� ���������
                Intent intentSubProduct = new Intent(ProductActivity.this, SubProductActivity.class);
                // � ���� typeProduct ������ ��������� �����, �� ������� ����� ����� ��� ���������
                intentSubProduct.putExtra("typeProduct", typeProduct);
                startActivityForResult(intentSubProduct, NUMBER_SUB_PRODUCT_ACTIVITY);

            }
        });


    }

    //�������, ������������� ��� �������� �� �������� ������ ���������
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // TextView infoTextView = (TextView) findViewById(R.id.textViewProductListTest);

        if (requestCode == NUMBER_SUB_PRODUCT_ACTIVITY) {
            if (resultCode == RESULT_OK) {
        ////        String productName = data.getStringExtra(SubProductActivity.PRODECTS_LIST);
                //�������� ������ ��������� ���������
                HashMap<String, String> hashMapProducts = (HashMap<String, String>)data.getSerializableExtra("PRODECTS_LIST");

                if (hashMapProducts.isEmpty()){ //���� ������������ ������ �� ������, �� ������ � �� ������
                    selectionProduct.setText("no change");
                } else {
                    //��� ��������, ��������� �������� ������ � �����
                    //for (String key : hashMapProducts.keySet()) {
                    //    selectionProduct.append(hashMapProducts + " ");
                    //}

                    //�������, ��� ������, � ����� ������� ��������� �� ��������
                    //for (String value : hashMapProducts.values()) {
                    //    selectionProduct.setText(value);
                    //}

                    //selectionProduct.setText(typeProduct);//��������, ��� �� ����� typeProduct

                    //������� �� ������� ���� ��������� ��� ����� ���������, ������� ������ ��� �������������
                    if (generalMapProduct.isEmpty()){
                        //���� ������� ���� ������, �� ������ ������
                    }else{
                        for(Iterator<HashMap.Entry<String, String>> it = generalMapProduct.entrySet().iterator(); it.hasNext(); ) {
                            HashMap.Entry<String, String> entry = it.next();
                            if(entry.getValue().equals(typeProduct)) {
                                it.remove();
                            }
                        }
                    }

                    //��������� ������� ���� � ���������.
                    generalMapProduct.putAll(hashMapProducts);
                    //selectionProduct.setText(typeProduct);

                    selectionProduct.setText("");
                    for (String key : generalMapProduct.keySet()) {
                        selectionProduct.append("  " + key + "  ");
                    }
                    //selectionProduct.append(generalMapProduct + " ");

                }
                    //selectionProduct.setText(hashMapProducts.get("meat"));


            }else {
                selectionProduct.setText("����� �����-�� ���������7"); // ����� �����-�� ���������
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
        Intent intentTableActivity = new Intent(ProductActivity.this, TableActivity.class);
        startActivity(intentTableActivity);
    }

    //��������� �������� � ������������ � ������ ���������
    public void onClickBack2(View view) {
        finish();
    }
}
