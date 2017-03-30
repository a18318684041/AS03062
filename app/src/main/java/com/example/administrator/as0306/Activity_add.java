package com.example.administrator.as0306;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class Activity_add extends AppCompatActivity{
    private EditText edt_name;
    private EditText edt_age;
    private EditText edt_address;
    private Button btn_add;
    private ImageView headimg;
    private Button btn_sel;
    private String picpath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
/*        String[] permission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(Activity_add.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(permission,1001);
        }*/

    }

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/

    private void initView() {
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_age = (EditText) findViewById(R.id.edt_age);
        edt_name = (EditText) findViewById(R.id.name);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_sel = (Button) findViewById(R.id.btn_sel);
        headimg = (ImageView) findViewById(R.id.headimg);
        //选择头像的地方
        btn_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1001);
            }
        });



        //final String path ="/storage/emulated/0/DCIM/Screenshots/Screenshot_2017-02-23-11-15-39-15.png";


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将上面的内容添加到后台
                File jpg = new File(picpath);
                if(jpg.exists()){
                    Toast.makeText(Activity_add.this,"exsits",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Activity_add.this,picpath,Toast.LENGTH_SHORT).show();
                }
                final BmobFile bmobFile = new BmobFile(new File(picpath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        String address = edt_address.getText().toString();
                        String name = edt_name.getText().toString();
                        String age = edt_age.getText().toString();
                        if(name!=null){
                            Person p = new Person();
                            p.setName(name);
                            p.setAddress(address);
                            p.setHeadimg(bmobFile);
                            p.setAge(Integer.parseInt(age));
                            p.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Toast.makeText(Activity_add.this,"success",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Activity_add.this,"failed",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            finish();
                        }
                    }
                });

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 1001
                    && resultCode == Activity.RESULT_OK){
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                picpath = picturePath;
                cursor.close();
                headimg.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                Toast.makeText(Activity_add.this,picturePath,Toast.LENGTH_LONG).show();
            }
        }
    }


}
