package fangdichan.com.fangdichan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddHomeActivity extends AppCompatActivity {

    @BindView(R.id.propertyName)
    EditText edpropertyName;
    @BindView(R.id.price)
    EditText edprice;
    @BindView(R.id.phone)
    EditText edphone;
    @BindView(R.id.propeSize)
    EditText edpropeSize;
    @BindView(R.id.propeIntrote)
    EditText edpropeIntrote;
    @BindView(R.id.submit)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        ButterKnife.bind(this);
        initdata();
    }

    private void initdata() {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String propertyName  = edpropertyName.getText().toString();
                    String price = edprice.getText().toString();
                    String phone = edphone.getText().toString();
                    String propeSize = edpropeSize.getText().toString();
                    String propeIntrote = edpropeIntrote.getText().toString();
                    if(propertyName.isEmpty()||price.isEmpty()||phone.isEmpty()||propeSize.isEmpty()||propeIntrote.isEmpty()){
                        Toast.makeText(AddHomeActivity.this, "请填写完整信息！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            });

    }
}
