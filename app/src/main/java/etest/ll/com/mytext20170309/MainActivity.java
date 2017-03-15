package etest.ll.com.mytext20170309;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RecyclerView的使用
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btnNormal, R.id.btnStagger})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNormal:
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                break;
            case R.id.btnStagger:
                break;
        }
    }
}


