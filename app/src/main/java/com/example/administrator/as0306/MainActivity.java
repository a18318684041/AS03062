package com.example.administrator.as0306;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

public class MainActivity extends AppCompatActivity {

    private  StringBuffer b = new StringBuffer();
    private Button btn_add;
    private RecyclerView recyclerView;
    private List<Person> pList = new ArrayList<>();
    private  MyAdapter adpter;

    private EditText edt_serch;
    private Button btn_serch;
    private Button btn_logout;
    private Button btn_change;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进行初始化
        Bmob.initialize(this, "f8838f4d3eddeeab8f7c52ba7517a893");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adpter = new MyAdapter(pList);
        LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adpter);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Activity_add.class);
                startActivity(intent);
            }
        });



        edt_serch = (EditText) findViewById(R.id.edt_serch);
        btn_serch = (Button) findViewById(R.id.btn_serch);
        btn_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_serch.getText().toString();
                if(name!=null){
                    String bql = "select * from Person where name = '"+ name +"'";
                    new BmobQuery<Person>().doSQLQuery(bql, new SQLQueryListener<Person>() {
                        @Override
                        public void done(BmobQueryResult<Person> bmobQueryResult, BmobException e) {
                            if(e==null){
                                List<Person> result = bmobQueryResult.getResults();
                                adpter.changData(result);
                            }else{
                                Toast.makeText(MainActivity.this,"搜索失败",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    query();
                }
            }
        });

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Activity_login.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"你已退出登录，欢迎下次再来",Toast.LENGTH_LONG).show();
            }
        });


        btn_change = (Button) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Activity_changepas.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        query();
    }

    private void query() {
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if(e==null){
                    pList.clear();
                    pList.addAll(list);
                    pList = list;
                }else{
                    Log.i("hhh",e.toString());
                }
            }
        });
    }
}
