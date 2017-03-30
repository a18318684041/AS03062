package com.example.administrator.as0306;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/2/20 0020.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private  List<Person> personList;

    public MyAdapter(List<Person> personList){
        this.personList = personList;
    }


    class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_name;
        TextView tv_age;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            tv_age = (TextView) itemView.findViewById(R.id.age);
            tv_name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public void changData(List<Person> personList) {
        this.personList = personList;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person p = personList.get(position);
        BmobFile file  = p.getHeadimg();
        if(file!=null){
            String imgurl = file.getFileUrl();
            if(imgurl!=null){
                Glide.with(holder.imageView.getContext()).load(imgurl).into(holder.imageView);
            }
        }
        holder.tv_age.setText(String.valueOf(p.getAge()));
        holder.tv_name.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

}

