package etest.ll.com.mytext20170309;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecycleView;
    private List<String> mData;
    private LinearAdapter mLinearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_second);
        ButterKnife.bind(this);

        //初始化相关布局
        initView();
        initData();
    }

    private void initView() {
        //1.设置布局管理器：让他展示的样式
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //2.如果添加或删除item，可以设置动画,他为我们提供一个可以直接使用的动画：DefaultItemAnimator
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        // 3. 设置分割线:默认提供的一个：DividerItemDecoration，都可以自己定义，可以在item布局中设置
        mRecycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        // 4. 设置适配器
        mLinearAdapter=new LinearAdapter();
        mRecycleView.setAdapter(mLinearAdapter);

        //设置item的点击事件
        mLinearAdapter.setOnItemClickListener(new LinearAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(SecondActivity.this, "click"+position, Toast.LENGTH_SHORT).show();
            }
        });
        //6 长按事件
        mLinearAdapter.setOnItemLongClickListener(new LinearAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(SecondActivity.this, "onlongClick"+position, Toast.LENGTH_SHORT).show();
                // 删除数据
              /*  mLinearAdapter.removeData(position);*/
            }
        });
    //7设置拖动和滑动删除
        /*ItemTouchHelper.Callback callback=new ItemTouchHelper.Callback() {


            *//**
             *
             * 方向：
             * dirgDirs：拖动LEFT,RIGHT,UP,DOWN,START,END
             * swipeDirs：滑动：LEFT,RIGHT,UP,DOWN,START,END
             *如果设置为0的时候，表示没有相应的功能
             *//*
            //拿到设置的方向移动的方向设置
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //设置的方向（拖动或滑动）
                int swipeFlags=0;
                int dragFlags=0;
                if(recyclerView.getLayoutManager()instanceof GridLayoutManager){//查看是否属于网格布局
                    dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                    swipeFlags=0;
                }else {
                    dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
                    swipeFlags=ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlags,swipeFlags);//将我们设置的flags设置给ItemTouchHelper
            }
                //拖动的设置
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }
            //滑动删除
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //
            }
        };*/

             //使用callback的子类来实现
        ItemTouchHelper.Callback callback=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
            @Override
            //拖动
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition=viewHolder.getAdapterPosition();//得到拖动的ViewHolder 的position
                int toPosition=target.getAdapterPosition();// 得到目标的viewHolder的position
                Log.e("d","fromPosition" + fromPosition);
                Log.e("d","toPosition" + toPosition);
                if(fromPosition<toPosition){//向下移动
                    //移动后下标值要改变，所以利用for循环来将中间的位置交换
                    for (int i=fromPosition;i<toPosition;i++){
                        //数据集合，通过集合的工具类Collection
                        Collections.swap(mData,i,i+1);
                    }
                }else {//向上移动
                    for(int i=fromPosition;i>toPosition;i--){
                        Collections.swap(mData,i,i-1);
                    }
                }
                //因为数据改变了，所以需通知适配器
                mLinearAdapter.itemMove(fromPosition,toPosition,mData);
                //表示执行了拖动
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //滑动的时候删除
                int positon=viewHolder.getAdapterPosition();
                mLinearAdapter.removeData(positon);

            }
        };
        //拖动滑动的帮助类
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        //跟RecycleView关联起来
        itemTouchHelper.attachToRecyclerView(mRecycleView);
    }


    @OnClick(R.id.button)
    public void addData(){
        mLinearAdapter.addData(5);
    }

    private void initData() {
        mData=new ArrayList<>();
        for (int i = 'A'; i <='Z' ; i++) {
            mData.add(""+(char)i);
        }
    mLinearAdapter.setMdata(mData);
    }

}
