package kr.ac.poly.smart_coin_saver;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AllMoney extends AppCompatActivity {
    Button btn_Allmoney;
EditText mMemoEdit = null;
public CreateText mTextFileManager = new CreateText(this);
    int Goal=0;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allmoney);
     mMemoEdit=(EditText)findViewById(R.id.edtGoal);
        mMemoEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mMemoEdit.getText().toString().equals("금액을 입력 하세요"))
                    mMemoEdit.setText("");
            }
        });
    }
    public void btnOkAll(View v){
        EditText editGoal=(EditText)findViewById(R.id.edtGoal);
        btn_Allmoney=(Button) findViewById(R.id.btnAllmoney);
        TextView txtRight=(TextView)findViewById(R.id.txtRight);
        String wait = editGoal.getText().toString().trim();
        try {

                if(v.getId()==R.id.btnAllmoney) {
                    String memoData =mMemoEdit.getText().toString();
                    mTextFileManager.save(memoData);
                    mMemoEdit.setText("");

            }

            Goal = Integer.parseInt(wait);
            Intent intent=new Intent(AllMoney.this, MainActivity.class);
            intent.putExtra("injavalue",wait);

            startActivity(intent);
            finish();

        }catch(NumberFormatException e){
            Toast.makeText(this, "숫자만 입력하세요", Toast.LENGTH_SHORT).show();
        }
    }
}
