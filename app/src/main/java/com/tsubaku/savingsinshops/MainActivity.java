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


public class MainActivity extends ActionBarActivity {
    public TextView selectionMarket; //переменная для текстовой метки списка выбранных магазинов
    public ListView listViewMarket; //переменная для списка магазинов
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // получаем экземпляр элемента ListView
        listViewMarket = (ListView)findViewById(R.id.listViewMarket);
        //получаем значение текстовой метки списка выбранных магазинов
        selectionMarket = (TextView) findViewById(R.id.textViewMarketListTest);
        // определяем массив типа String с названиями магазинов
        final String[] marketName = getResources().getStringArray(R.array.marketNames);
        // используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, marketName);
        listViewMarket.setAdapter(adapter); //присобачиваем адаптер к списку магазинов

        //Создаём слушателя для списка магазинов
        listViewMarket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
               selectionMarket.setText("");

                //Создаём массив значений, к которым можно получить доступ
                SparseBooleanArray chosenMarket = ((ListView) parent).getCheckedItemPositions();
                //Перебираем весь массив
                for (int i = 0; i < chosenMarket.size(); i++) {
                    // если пользователь выбрал пункт списка, то выводим его в TextView.
                    if (chosenMarket.valueAt(i)) {
                        //Выводим отмеченные магазины в метку
                        selectionMarket.append(marketName[chosenMarket.keyAt(i)] + " ");
                    }
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //Создаём активити выбора продуктов
    public void onClickNext1(View view) {

        //Создаём интент для передачи данных в другую активити
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        // в ключ listMarkets пихаем текст из метки списка выбранных магазинов
        intent.putExtra("listMarkets", selectionMarket.getText().toString());
        //стартуем вторую активити
        startActivity(intent);
    }



}
