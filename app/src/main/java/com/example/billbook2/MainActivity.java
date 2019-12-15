package com.example.billbook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final int OUT = 1;
    private static final int IN = 2;
    private DBManager mgr;
    private EditText etCount,etDescribe;
    private RadioGroup radioGroup;
    private TextView tvInfo;
    private Button btnAdd;
    private Button btnList;
    private int countType = OUT ;
    public  static boolean isFirst=true;
    private SharedPreferences shp;
    private String[] s = new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. activity_main );
        // 初始化 DBManager
        mgr = new DBManager(this);
        etCount = (EditText) findViewById(R.id. etCount );
        etDescribe = (EditText) findViewById(R.id. etDescribe );
        radioGroup = (RadioGroup) findViewById(R.id. radioGroup );
        tvInfo = (TextView)findViewById(R.id. tvInfo );
        btnAdd = (Button)findViewById(R.id. btnAdd );
        btnList = (Button) findViewById(R.id.btnList);//添加进入到ListView的按钮

        LinkedList<Integer> list = mgr.getids();
        int size = list.size();
        s = new String[size];
        for (int i = 0; i < size; i++) {
            s[i] = list.get(i) + "";
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id. radioOut :
                        countType = OUT ;
                        break;
                    case R.id. radioIn :
                        countType = IN ;
                        break;
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.test);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,s);
                ListView listview=(ListView)findViewById(R.id.list_view);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {//监听点击位置
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent();
                        intent.setClass(com.example.billbook2.MainActivity.this,MainActivity1.class);//确当跳转的双方
                        intent.putExtra("id",position);
                        startActivity(intent);//执行跳转
                    }
                });
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Count count = new Count();
                long time = System. currentTimeMillis ();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str = format.format(new Date(time));
                count.setDate(str);
                count.setMoney(Double. parseDouble (etCount.getText().toString()));
                count.setDescribe(etDescribe.getText().toString());
                count.setType(countType + "");
                count.save();
                mgr.insert(count);      // 插入数值
                resetInfo();
            }
        });
        resetInfo();
        if(isFirst) { //判断是不是第一次登陆
            isFirst=false;
            shp = getSharedPreferences(getResources().getString(R.string.shp), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shp.edit();
            if (shp.getAll().isEmpty()) {
                Toast.makeText(MainActivity.this, "第一次登录", Toast.LENGTH_LONG).show();
                editor.putLong("last_time", System.currentTimeMillis());
            } else {
                int c = shp.getInt("count", 1);
                Long t = shp.getLong("last_time", 0);
                if (t != 0 && c != 1) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String str = format.format(new Date(t));
                    Toast.makeText(MainActivity.this, "上次登录时间：" + str + "  " + "登录次数：" + c, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
                c++;
                editor.putInt("count", c);
            }
            editor.apply();
        }
    }

    public void resetInfo(){
        Double out = mgr.getResult( OUT );
        Double in = mgr.getResult( IN );
        Double all = in - out;
        tvInfo.setText("总计支出："+out+"  总计收入："+in+" \n 结余：" +all+"。");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 应用的最后一个 Activity 关闭时应释放 DB
        mgr.closeDB();
    }
}

