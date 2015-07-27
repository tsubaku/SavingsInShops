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
    public TextView selectionMarket; //переменна€ дл€ текстовой метки списка выбранных магазинов
    public ListView listViewMarket; //переменна€ дл€ листа магазинов
    public TextView selectionProduct; //переменна€ дл€ текстовой метки списка выбранных продуктов
    public ListView listViewProduct; //переменна€ дл€ листа продуктов
    public String typeProduct; //текстова€ метка типа продуктов (м€со/овощи/etc)
    public ArrayList<String> listProducts = new ArrayList(); //общий список продуктов
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

        // получаем экземпл€р элемента ListView
        listViewProduct = (ListView)findViewById(R.id.listViewProduct);
        //получаем значение текстовой метки списка выбранных продуктов
        selectionProduct = (TextView) findViewById(R.id.textViewProductListTest);
        //получаем значение текстовой метки списка выбранных магазинов
        selectionMarket = (TextView) findViewById(R.id.textViewMarketListTest);
        // определ€ем массив типа String
        final String[] productName = getResources().getStringArray(R.array.productNames);
        // используем адаптер данных
        ArrayAdapter<String> adapterProduct = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, productName);
        listViewProduct.setAdapter(adapterProduct); //присобачиваем адаптер к списку продуктов

        //«абираем из ћайнјктивити список выбранных магазинов
        String listMarket = "—писок выбранных продуктов не пришЄл. Ёто странно";
        listMarket = getIntent().getExtras().getString("listMarkets");
        //ѕрописываем их в тектовую метку
        selectionMarket.setText(listMarket);

        //—оздаЄм слушател€ дл€ списка продуктов
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
        //        selectionProduct.setText(""); //ќбнуление метки списка выбранных продуктов

                //—оздаЄм массив значений, к которым можно получить доступ
                SparseBooleanArray chosenProduct = ((ListView) parent).getCheckedItemPositions();
                //ѕеребираем весь массив
            //    for (int i = 0; i < chosenProduct.size(); i++) {
                    // если пользователь выбрал пункт списка, то выводим его в TextView.
             //       if (chosenProduct.valueAt(i)) {
                        //¬ыводим отмеченные продукты в метку
              //          selectionProduct.append(productName[chosenProduct.keyAt(i)] + " ");
             //       }
            //    }

                //ѕодготавливаем активити выбора продуктов
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString(); // получаем текст нажатого элемента
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
                // «апускаем активность выбора конкретных продуктов
                Intent intentSubProduct = new Intent(ProductActivity.this, SubProductActivity.class);
                // в ключ typeProduct пихаем текстовую метку, по которой будет видно тип продуктов
                intentSubProduct.putExtra("typeProduct", typeProduct);
                startActivityForResult(intentSubProduct, ACTIVITY_NUMBER);

            }
        });


    }

    //‘ункци€, срабатывающа€ при возврате из активити выбора продуктов
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // TextView infoTextView = (TextView) findViewById(R.id.textViewProductListTest);

        if (requestCode == ACTIVITY_NUMBER) {
            if (resultCode == RESULT_OK) {
                String productName = data.getStringExtra(SubProductActivity.PRODECTS_LIST);
                selectionProduct.setText(productName);




            }else {
                selectionProduct.setText("фигн€ кака€-то случилась"); // фигн€ кака€-то случилась
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

    //ѕереходим в активити с таблицей
    public void onClickNext2(View view) {
        Intent intent = new Intent(ProductActivity.this, TableActivity.class);
        startActivity(intent);
    }

    //«акрываем активити и возвращаемс€ к выбору магазинов
    public void onClickBack2(View view) {
        finish();
    }
}
