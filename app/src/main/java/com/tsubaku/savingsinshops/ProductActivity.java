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
    public TextView selectionMarket;    //переменная для текстовой метки списка выбранных магазинов
    public ListView listViewMarket;     //переменная для листа магазинов
    public TextView selectionProduct;   //переменная для текстовой метки списка выбранных продуктов
    public ListView listViewProduct;    //переменная для листа продуктов
    public String typeProduct;          //текстовая метка типа продуктов (мясо/овощи/etc)
    public HashMap<String, String> generalMapProduct = new HashMap<String, String>();   //Общий список выбранных продуктов
    public HashMap<String, String> mapProduct = new HashMap<String, String>();          //Список части выбранных продуктов
    static final private int NUMBER_SUB_PRODUCT_ACTIVITY = 0;                           //Номер активити выбора продуктов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listViewProduct = (ListView)findViewById(R.id.listViewProduct);             //ЛистВью продуктов
        selectionProduct = (TextView) findViewById(R.id.textViewProductListTest);   //Список выбранных продуктов
        selectionMarket = (TextView) findViewById(R.id.textViewMarketListTest);     //Список выбранных магазинов
        final String[] productName = getResources().getStringArray(R.array.productNames);//Массив для строк типов продуктов
        // используем адаптер данных
        ArrayAdapter<String> adapterProduct = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productName);
        listViewProduct.setAdapter(adapterProduct);     //присобачиваем адаптер к списку продуктов

        //Забираем из МайнАктивити список выбранных магазинов
        String listMarket = "Список выбранных продуктов не пришёл. Это странно";
        listMarket = getIntent().getExtras().getString("listMarkets");
        selectionMarket.setText(listMarket);    //Показываем список выбранных магазинов

        //Создаём слушателя для списка продуктов
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
        //        selectionProduct.setText(""); //Обнуление метки списка выбранных продуктов

                //Создаём массив значений, к которым можно получить доступ
                SparseBooleanArray chosenProduct = ((ListView) parent).getCheckedItemPositions();
                //Перебираем весь массив
            //    for (int i = 0; i < chosenProduct.size(); i++) {
                    // если пользователь выбрал пункт списка, то выводим его в TextView.
             //       if (chosenProduct.valueAt(i)) {
                        //Выводим отмеченные продукты в метку
              //          selectionProduct.append(productName[chosenProduct.keyAt(i)] + " ");
             //       }
            //    }

                //Подготавливаем активити выбора продуктов
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString(); // получаем текст нажатого элемента
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
                // Запускаем активность выбора конкретных продуктов
                Intent intentSubProduct = new Intent(ProductActivity.this, SubProductActivity.class);
                // в ключ typeProduct пихаем текстовую метку, по которой будет видно тип продуктов
                intentSubProduct.putExtra("typeProduct", typeProduct);
                startActivityForResult(intentSubProduct, NUMBER_SUB_PRODUCT_ACTIVITY);

            }
        });


    }

    //Функция, срабатывающая при возврате из активити выбора продуктов
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // TextView infoTextView = (TextView) findViewById(R.id.textViewProductListTest);

        if (requestCode == NUMBER_SUB_PRODUCT_ACTIVITY) {
            if (resultCode == RESULT_OK) {
        ////        String productName = data.getStringExtra(SubProductActivity.PRODECTS_LIST);
                //Забираем список выбранных продуктов
                HashMap<String, String> hashMapProducts = (HashMap<String, String>)data.getSerializableExtra("PRODECTS_LIST");

                if (hashMapProducts.isEmpty()){ //Если пользователь ничего не выбрал, то ничего и не делаем
                    selectionProduct.setText("no change");
                } else {
                    //Тут проверка, выводящая принятый хешмап в метку
                    //for (String key : hashMapProducts.keySet()) {
                    //    selectionProduct.append(hashMapProducts + " ");
                    //}

                    //Вариант, как узнать, с какой группой продуктов мы работаем
                    //for (String value : hashMapProducts.values()) {
                    //    selectionProduct.setText(value);
                    //}

                    //selectionProduct.setText(typeProduct);//проверка, что мы видим typeProduct

                    //Удаляем из главной мапы продуктов тот класс продуктов, который только что редактировали
                    if (generalMapProduct.isEmpty()){
                        //Если главная мапа пустая, не делать ничего
                    }else{
                        for(Iterator<HashMap.Entry<String, String>> it = generalMapProduct.entrySet().iterator(); it.hasNext(); ) {
                            HashMap.Entry<String, String> entry = it.next();
                            if(entry.getValue().equals(typeProduct)) {
                                it.remove();
                            }
                        }
                    }

                    //Суммируем главную мапу и пришедшую.
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
                selectionProduct.setText("фигня какая-то случилась7"); // фигня какая-то случилась
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

    //Переходим в активити с таблицей
    public void onClickNext2(View view) {
        Intent intentTableActivity = new Intent(ProductActivity.this, TableActivity.class);
        startActivity(intentTableActivity);
    }

    //Закрываем активити и возвращаемся к выбору магазинов
    public void onClickBack2(View view) {
        finish();
    }
}
