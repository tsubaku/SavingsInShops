package com.tsubaku.savingsinshops;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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

    TextView customDialog_TextView;
    EditText customDialog_EditText;
    Button customDialog_Update, customDialog_Dismiss;
    static final int CUSTOM_DIALOG_ID = 0;

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


//======= Диалог выбора новой цены ======(
        gridViewTable.setOnItemSelectedListener(this);  //Присоединяем слушателя для действий с ячейками таблицы
        gridViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Действия при клике по элементу таблицы
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO Auto-generated method stub
                //mContacts[position] = "tt";
                ///Intent tableIntentForChange = new Intent();
                ///tableIntentForChange.putExtra("position", position);//Сохраняем № позиции в таблице

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                //navigation.this.startActivity(intent);

                showDialog(CUSTOM_DIALOG_ID, bundle);

                 //Toast.makeText(getApplicationContext(), mContacts[position], Toast.LENGTH_SHORT).show();
           }
        });
//=========================================)

    //    gridViewTable.setOnItemSelectedListener(this);  //Присоединяем слушателя для действий с ячейками таблицы
    //    gridViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Действия при клике по элементу таблицы
    //        @Override
    //        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO Auto-generated method stub
    //            textViewProduct.setText("changed element: " + mAdapter.GetItem(position));

                //При нажатии на ячейку, вызываем диалог установки нового значения цены продукта
    //            AlertDialog.Builder builder = new AlertDialog.Builder(TableActivity.this);
   //             builder.setTitle("Edit price")
    //                    .setMessage("Test")
     //                   .setIcon(R.drawable.cell)
    //                    .setCancelable(false)
    //                    .setNegativeButton("Okay",
    //                            new DialogInterface.OnClickListener() {
    //                                public void onClick(DialogInterface dialog, int id) {
    //                                    dialog.cancel();
    //                                }
    //                            });
    //            AlertDialog alert = builder.create();
    //            alert.show();
                //Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();

    //        }
    //    });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        // TODO Auto-generated method stub
        textViewProduct.setText("changed element= " + mAdapter.GetItem(position));
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
                if (i == 0){
                    mContacts[i] = "";//В первую ячейку гридВью ничего не кладём
                } else {
                    mContacts[i] = "no info";
                }

            }

            this.mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
//---(
    //        ImageView imageView;
    //        if (convertView == null) {
                // if it's not recycled, initialize some attributes
    //            imageView = new ImageView(mContext);
    //            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
    //           imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    //            imageView.setPadding(8, 8, 8, 8);
    //        } else {
    //            imageView = (ImageView) convertView;
    //        }

    //        imageView.setImageResource(R.drawable.cell);
    //        return (imageView);

//---)
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

//=== Слушатели для кнопок в диалоге выбора цены ========================(
    private Button.OnClickListener customDialog_UpdateOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
        // TODO Auto-generated method stub
            //customDialog_TextView.setText(customDialog_EditText.getText().toString());
            customDialog_EditText.setText("");//Обнулим поле для цены

            //Прописываем новую цену в таблицу
            Bundle bundle = getIntent().getExtras();
            int m = bundle.getInt("position");

            //Intent intentT = getIntent();
        //    int m = getIntent().getExtras().getInt("position");
            //Toast.makeText(getApplicationContext(), mContacts[m], Toast.LENGTH_SHORT).show();
            mContacts[m] = "777";
            //Toast.makeText(getApplicationContext(), Integer.toString(m), Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();

            dismissDialog(CUSTOM_DIALOG_ID);
        }
    };

    private Button.OnClickListener customDialog_DismissOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            removeDialog(CUSTOM_DIALOG_ID);
        }
    };
//==========================================================================)

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
// TODO Auto-generated method stub
        Dialog dialog = null;;
        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(TableActivity.this);

                dialog.setContentView(R.layout.change);
                dialog.setTitle(R.string.ChangePriceText);

                customDialog_EditText = (EditText)dialog.findViewById(R.id.customDialog_EditText);
                customDialog_TextView = (TextView)dialog.findViewById(R.id.textView2);
                customDialog_Update = (Button)dialog.findViewById(R.id.customDialog_Update);
                customDialog_Dismiss = (Button)dialog.findViewById(R.id.customDialog_Dismiss);

                customDialog_Update.setOnClickListener(customDialog_UpdateOnClickListener);
                customDialog_Dismiss.setOnClickListener(customDialog_DismissOnClickListener);

                break;
        }
        return dialog;
    }

}
