package com.example.administrator.as0306;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class Activity_changepas extends AppCompatActivity{
    private EditText oldpassword;
    private EditText edt_password;
    private EditText edt_confirm;

    private Button btn_queren;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        initView();
    }

    private void initView() {
        oldpassword = (EditText) findViewById(R.id.oldpassword);
        edt_password = (EditText) findViewById(R.id.newpassword);
        edt_confirm = (EditText) findViewById(R.id.newpassword2);
        btn_queren = (Button) findViewById(R.id.btn_queren);
        btn_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpass = edt_password.getText().toString();
                String confirm = edt_confirm.getText().toString();
                String old =  oldpassword.getText().toString();

                BmobUser.updateCurrentUserPassword(old, newpass, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Intent intent = new Intent();
                            intent.setClass(Activity_changepas.this,Activity_login.class);
                            startActivity(intent);
                            BmobUser.logOut();
                            Toast.makeText(Activity_changepas.this,"修改成功",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(Activity_changepas.this,"修改失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }

}
