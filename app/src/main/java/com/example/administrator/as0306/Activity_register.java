package com.example.administrator.as0306;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class Activity_register extends AppCompatActivity{
    private EditText edt_user;
    private EditText edt_password;
    private EditText edt_confirm;
    private EditText edt_phone;
    private EditText edt_code;
    private Button btn_code;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        edt_user = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_paaword);
        edt_confirm = (EditText) findViewById(R.id.edt_confirm);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_code = (EditText) findViewById(R.id.edt_code);
        btn_code = (Button) findViewById(R.id.btn_getcode);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String phone = edt_phone.getText().toString();
                BmobSMS.requestSMSCode(phone, "test", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if(e==null){
                           Toast.makeText(Activity_register.this,"发送成功",Toast.LENGTH_LONG).show();
                        }
                    }
                }) ;
            }
        });

      btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String code = edt_code.getText().toString();
            final String phone  =edt_phone.getText().toString();
                BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        String username = edt_user.getText().toString();
                        String password = edt_password.getText().toString();
                        String confirm = edt_confirm.getText().toString();
                        if(password.equals(confirm)){
                            BmobUser user = new BmobUser();
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setMobilePhoneNumber(phone);
                            user.setMobilePhoneNumberVerified(true);
                            user.signUp(new SaveListener<BmobUser>() {
                                @Override
                                public void done(BmobUser s, BmobException e) {
                                    if(e == null){
                                        Toast.makeText(Activity_register.this,"注册成功",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            Intent intent = new Intent();
                            intent.setClass(Activity_register.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(Activity_register.this,"密码不一致，注册失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }




}
