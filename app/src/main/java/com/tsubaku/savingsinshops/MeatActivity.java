package com.tsubaku.savingsinshops;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MeatActivity extends ActionBarActivity {
    public ListView listViewMeat; //���������� ��� ������ ����
    public TextView selectionMarket; //���������� ��� ��������� ����� ������ ��������� ���������

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat);

        //�������� �� ��������������� ����� ���� ���������
        String listMarket = "rrr";
        listMarket = getIntent().getExtras().getString("listMarkets");
        //����������� �� � �������� �����
        selectionMarket.setText(listMarket);

        // �������� ��������� �������� ListView
        listViewMeat = (ListView)findViewById(R.id.listViewMeat);
        // ���������� ������ ���� String
        final String[] meatName = getResources().getStringArray(R.array.meatNames);
        // ���������� ������� ������
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, meatName);
        listViewMeat.setAdapter(adapter); //������������� ������� � ������ ���������
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meat, menu);
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
        finish();
    }

    //��������� �������� � ������������ � ������ ���������
    public void onClickCancel3(View view) {
        finish();
    }

}
