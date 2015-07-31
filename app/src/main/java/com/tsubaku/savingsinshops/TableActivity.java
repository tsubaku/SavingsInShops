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
    public ArrayList<String> listChangeMarket;  //Список выбранных магазинов
    public HashMap<String, String> generalMapProduct = new HashMap<String, String>();//Общий список выбранных продуктов
    public HashMap<String, String> numberMapProduct = new HashMap<String, String>();//Название продукта/номер в ГридВью

    public TextView textViewProduct; //метка

    private GridView  gridViewTable; //таблица для вывода цен
    private DataAdapter mAdapter; //адаптер для таблицы
    public static int nCell; //количество ячеек в ГридВью
    private static String[] mContacts;//Содержимое ячеек ГридВью

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //Забираем из МайнАктивити список выбранных магазинов и продуктов
        Intent intentMarket = getIntent();
        listChangeMarket = (ArrayList<String>)intentMarket.getSerializableExtra("putChangeMarketList");
        generalMapProduct = (HashMap<String, String>)intentMarket.getSerializableExtra("generalMapProduct");
        //Определяем количество ячеек в таблице
        nCell = listChangeMarket.size()*generalMapProduct.size() + listChangeMarket.size() + generalMapProduct.size() + 1;
        mContacts = new String[nCell];

        //Формируем таблицу
        textViewProduct = (TextView) findViewById(R.id.textViewProduct);//Текстовая метка
        gridViewTable = (GridView) findViewById(R.id.gridViewTable);    //Таблица
        mAdapter = new DataAdapter(getApplicationContext(),             //Адаптер для таблицы
                android.R.layout.simple_list_item_1);
        gridViewTable.setAdapter(mAdapter);

        gridViewTable.setNumColumns(listChangeMarket.size() + 1);//зададим количество столбцов таблицы
        //Прописываем в таблицу названия магазинов
        for (int i = 0; i < listChangeMarket.size(); i++) {
            mContacts[i+1] = listChangeMarket.get(i);
        }

        //Шаманим с мапой продуктов, заменяя V (значение) с группы продуктов на номер ячейки в ГридВью
        int i = 0;
        for(Map.Entry entry: generalMapProduct.entrySet()) {
            String key = (String)entry.getKey();//получить ключ
            String value = (String)entry.getValue();//получить значение
            int Pi = (listChangeMarket.size()+1)*(i+1);   //Pi=(n+1)(i+1)+1
            i++;
            numberMapProduct.put(key, Integer.toString(Pi));//Заодно вводим кошерную переменную
            mContacts[Pi] = key;
        }         //Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_SHORT).show();


        gridViewTable.setOnItemSelectedListener(this);  //Присоединяем слушателя для действий с ячейками таблицы
        gridViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Действия при клике по элементу таблицы
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                textViewProduct.setText("changed element: "
                        + mAdapter.GetItem(position));
                //gridViewTable.setNumColumns(3);//зададим количество столбцов таблицы
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

        // Конструктор
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

        // возвращает содержимое выделенного элемента списка
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

    //Закрываем активити и возвращаемся к выбору продуктов
    public void onClickBack2(View view) {
        Intent TableIntent = new Intent();
        TableIntent.putExtra("putChangeMarketList", listChangeMarket);//Сохраняем списки
        TableIntent.putExtra("generalMapProduct", generalMapProduct);//сохраняем общий список выбранных продуктов
        setResult(RESULT_OK, TableIntent);
        finish();
        //Создаём интент для передачи данных в другую активити
        //Intent intent = new Intent(ProductActivity.this, MainActivity.class);
        //startActivity(intent);
    }

}
