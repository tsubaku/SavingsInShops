package com.tsubaku.savingsinshops;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TableActivity extends Activity implements
        AdapterView.OnItemSelectedListener{
    public ArrayList<String> listChangeMarket;  //������ ��������� ���������
    public HashMap<String, String> generalMapProduct = new HashMap<String, String>();//����� ������ ��������� ���������
    public HashMap<String, String> numberMapProduct = new HashMap<String, String>();//�������� ��������/����� � �������

    public TextView textViewProduct; //�����

    private GridView  gridViewTable; //������� ��� ������ ���
    private DataAdapter mAdapter; //������� ��� �������
    public static int nCell; //���������� ����� � �������
    private static String[] mContacts;//���������� ����� �������

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //�������� �� ������������ ������ ��������� ��������� � ���������
        Intent intentMarket = getIntent();
        listChangeMarket = (ArrayList<String>)intentMarket.getSerializableExtra("putChangeMarketList");
        generalMapProduct = (HashMap<String, String>)intentMarket.getSerializableExtra("generalMapProduct");
        //���������� ���������� ����� � �������
        nCell = listChangeMarket.size()*generalMapProduct.size() + listChangeMarket.size() + generalMapProduct.size() + 1;
        mContacts = new String[nCell];

        //��������� �������
        textViewProduct = (TextView) findViewById(R.id.textViewProduct);//��������� �����
        gridViewTable = (GridView) findViewById(R.id.gridViewTable);    //�������
        mAdapter = new DataAdapter(getApplicationContext(),             //������� ��� �������
                android.R.layout.simple_list_item_1);
        gridViewTable.setAdapter(mAdapter);

        gridViewTable.setNumColumns(listChangeMarket.size() + 1);//������� ���������� �������� �������
        //����������� � ������� �������� ���������
        for (int i = 0; i < listChangeMarket.size(); i++) {
            mContacts[i+1] = listChangeMarket.get(i);
        }

        //������� � ����� ���������, ������� V (��������) � ������ ��������� �� ����� ������ � �������
        int i = 0;
        for(Map.Entry entry: generalMapProduct.entrySet()) {
            String key = (String)entry.getKey();//�������� ����
            String value = (String)entry.getValue();//�������� ��������
            int Pi = (listChangeMarket.size()+1)*(i+1);   //Pi=(n+1)(i+1)+1
            i++;
            numberMapProduct.put(key, Integer.toString(Pi));//������ ������ �������� ����������
            mContacts[Pi] = key;
        }         //Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_SHORT).show();


        gridViewTable.setOnItemSelectedListener(this);  //������������ ��������� ��� �������� � �������� �������
        gridViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //�������� ��� ����� �� �������� �������
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                textViewProduct.setText("changed element: "
                        + mAdapter.GetItem(position));
                //gridViewTable.setNumColumns(3);//������� ���������� �������� �������
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        // TODO Auto-generated method stub
        textViewProduct.setText("changed element: " + mAdapter.GetItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
        textViewProduct.setText("changed element: nothing");
    }

//====================== DataAdapter =====================================(

    public static class DataAdapter extends ArrayAdapter<String> {
        Context mContext;

        // �����������
        public DataAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId, mContacts);
            // TODO Auto-generated constructor stub

            for (int i = 0; i < nCell; i++){
                mContacts[i] = "no info";
            }

            this.mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            TextView label = (TextView) convertView;

            if (convertView == null) {
                convertView = new TextView(mContext);
                label = (TextView) convertView;
            }
            label.setText(mContacts[position]);
            return (convertView);
        }

        // ���������� ���������� ����������� �������� ������
        public String GetItem(int position) {
            return mContacts[position];
        }

    }
//=======================================================)






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table, menu);
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

    //��������� �������� � ������������ � ������ ���������
    public void onClickBack2(View view) {
        Intent TableIntent = new Intent();
        TableIntent.putExtra("putChangeMarketList", listChangeMarket);//��������� ������
        TableIntent.putExtra("generalMapProduct", generalMapProduct);//��������� ����� ������ ��������� ���������
        setResult(RESULT_OK, TableIntent);
        finish();
        //������ ������ ��� �������� ������ � ������ ��������
        //Intent intent = new Intent(ProductActivity.this, MainActivity.class);
        //startActivity(intent);
    }

}
