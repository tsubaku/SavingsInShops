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
import java.util.Map;
import java.util.TreeMap;


public class SubProductActivity extends ActionBarActivity {
    public TextView selectionProduct; //переменная для текстовой метки списка выбранных продуктов
    public ListView listViewVegetables; //переменная для списка овощей
    public ListView listViewProducts; //переменная для списка продуктов
    public HashMap<String, String> mapProduct = new HashMap<String, String>();
    String productGroup; //строка для V - группы продуктов в HashMap

    public final static String PRODECTS_LIST = "com.tsubaku.savingsinshops.Products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_product);


        //получаем значение текстовой метки списка выбранных продуктов
        selectionProduct = (TextView) findViewById(R.id.textViewProductListTest);
        selectionProduct.setText("");   //Сразу её обнуляем

        //Забираем из ПродактАктивити метку типа продуктов
        String typeSubProduct = "type";
        typeSubProduct = getIntent().getExtras().getString("typeProduct");
        //Прописываем их в тектовую метку
        //selectionProduct.setText(typeSubProduct);

        // получаем экземпляр элемента ListView для списка овощей/фруктов/etc
        listViewProducts = (ListView)findViewById(R.id.listViewSubProducts);

        //Смотрим, какой тип продуктов выбран и формируем ЛистВью для него
        final String[] Products;
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
        // используем адаптер данных
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, Products);
        listViewProducts.setAdapter(adapter3); //присобачиваем адаптер к списку продуктов

        //Создаём слушателя для списка продуктов
        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View itemClicked, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
                selectionProduct.setText("");//обнуляем старую метку списка продуктов

                //Создаём массив значений, к которым можно получить доступ
                SparseBooleanArray chosenProducts = ((ListView) parent).getCheckedItemPositions();
                //Перебираем весь массив
                for (int i = 0; i < chosenProducts.size(); i++) {
                    // если пользователь выбрал пункт из списка, то выводим его в TextView.
                    if (chosenProducts.valueAt(i)) {
                        //Выводим отмеченные продукты в метку
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

    //Переходим в активити с таблицей
    public void onClickOk3(View view) {

        //вернуть список выбранных продуктов в ПродактАктивити
        Intent answerSubProductIntent = new Intent();
        answerSubProductIntent.putExtra("PRODECTS_LIST", mapProduct);
        setResult(RESULT_OK, answerSubProductIntent);
        finish();
    }

    //Закрываем активити и возвращаемся к выбору магазинов
    public void onClickCancel3(View view) {
        finish();
    }

}
