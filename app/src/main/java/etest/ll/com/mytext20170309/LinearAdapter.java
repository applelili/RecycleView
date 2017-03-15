package etest.ll.com.mytext20170309;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/10.
 */

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.ViewHolder> {
    private List<String> mdata=new ArrayList<>();//创建数据源
    private OnItemClickListener monItemClickListener;
    private OnItemLongClickListener monItemLongClickListener;

    //数据填充
    public void setMdata(List<String> data){
        mdata.clear();
        mdata.addAll(data);
        notifyDataSetChanged();
    }

    //创建试图的viewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        ViewHolder holder=new ViewHolder(itemView);
        return holder;
    }
    // 视图和数据进行绑定
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTvItem.setText(mdata.get(position));

        if(monItemClickListener!=null){
            holder.mTvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 可以直接在这里完成，不方便在这里写的话
                    // 接口回调的方式
                    monItemClickListener.onItemClick(position);
                }
            });
        }

        if (monItemLongClickListener!=null){
                holder.mTvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    monItemLongClickListener.onItemLongClick(position);

                    // 不会触发点击的
                    return true;
                }
            });
        }
    }

    // item数量
    @Override
    public int getItemCount() {
        return mdata.size();
    }
    // 删除某条数据
    public void removeData(int position) {
        mdata.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mdata.size()-position);

        // 最常用
//        notifyDataSetChanged();
//        notifyItemRemoved(position);
//        notifyItemInserted(position);
//        notifyItemRangeChanged(position,mData.size()-position);
    }

    // 添加某条数据
    public void addData(int position) {
        mdata.add(position,"insert ok");
        notifyItemInserted(position);
        notifyItemRangeChanged(position,mdata.size()-position);
    }
        //移动item
    public void itemMove(int fromPosition, int toPosition, List<String> mData) {
        //刷新数据两种方式
        mdata.clear();
        mData.addAll(mData);
       //第二种利用集合工具类  利用这种方式那mData集合就不用传过来了
   /*     Collections.swap(mData,fromPosition,toPosition);*/
        notifyItemMoved(fromPosition,toPosition);//结构
        notifyItemRangeChanged(fromPosition,mData.size()-fromPosition);

    }


    class ViewHolder extends RecyclerView.ViewHolder{
     @BindView(R.id.item_text)
     TextView mTvItem;

     public ViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this,itemView);
     }
 }
    //设置监听的方法：实现我们接口的初始化
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        monItemClickListener=onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        monItemLongClickListener = onItemLongClickListener;
    }

    //设置点击监听
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    //设置长按监听
    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }

}
