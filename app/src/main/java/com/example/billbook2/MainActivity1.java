package com.example.billbook2;





import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class MainActivity1 extends AppCompatActivity {
    private Button btnLook;
    private DBManager db;
    private Button btnAlt;
    private EditText editText1;
    private int id = 0;
    private  Button btnDel;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnLook = (Button) findViewById(R.id.btnLook);
        editText1=(EditText)findViewById(R.id.look);//输出查看信息的窗口
        editText2=(EditText)findViewById(R.id.edit2);
        editText3=(EditText)findViewById(R.id.edit3);
        editText4=(EditText)findViewById(R.id.edit4);
        editText5=(EditText)findViewById(R.id.edit5);
        db=new DBManager(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);//获得了用户点击的数据库的哪一行的id
        Toast.makeText(MainActivity1.this,String.valueOf(id), Toast.LENGTH_LONG).show();
        btnLook.setOnClickListener(new View.OnClickListener() {//为查询按钮添加监听事件
            @Override
            public void onClick(View v) {
                String pass = db.Look(id);
                Toast.makeText(MainActivity1.this, pass, Toast.LENGTH_LONG).show();
                editText1.setText(pass);
            }
        });
       btnAlt = (Button) findViewById(R.id.btnAlt);
        btnAlt.setOnClickListener(new View.OnClickListener() {
             public void onClick(View view) {
                 int count = Integer.parseInt(editText2.getText().toString());
                 int type =  Integer.parseInt(editText3.getText().toString());
                 String date = editText4.getText().toString();
                 String describle = editText5.getText().toString();
                 db.Alter(id,count,type,date,describle);
                 Toast.makeText(MainActivity1.this, "success", Toast.LENGTH_LONG).show();



             }
        });

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {//为查询按钮添加监听事件
            @Override
            public void onClick(View v) {
                db.delete(id);
                Toast.makeText(MainActivity1.this, "success", Toast.LENGTH_LONG).show();

            }
        });




    }



}



