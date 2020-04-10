package com.example.mysmartrefreshdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * author : jim
 * date : 2020/4/9 12:22
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //数据源
    private List<String> mList;

    public MyAdapter(List<String> list) {
        mList = list;
    }

    //返回item个数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    //填充视图
    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.ViewHolder holder, final int position) {
        holder.mView.setText(mList.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.text);
        }
    }
}
