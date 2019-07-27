package com.example.applicateclass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class HowMuchActivity extends AppCompatActivity {
    public final String PREFERENCE = "com.example.applicateclass"; //저장, 불러오기 위한
    public String write_score = "write_score";
    private Context context;
    public int Grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howmuch);

        String grade[] = new String[4];

        /**********Grade는 1~4학년을 숫자 Grade: 1, 2, 3, 4로 표현하여 정보를 저장한다*******************/

        Intent intent_info = getIntent(); //데이터 수신 (학년 선택 수신)
        Boolean grade1_key = intent_info.getExtras().getBoolean("grade1_key");
        Boolean grade2_key= intent_info.getExtras().getBoolean("grade2_key");
        Boolean grade3_key= intent_info.getExtras().getBoolean("grade3_key");
        Boolean grade4_key= intent_info.getExtras().getBoolean("grade4_key");

        //받아온 정보 넣기(boolean->String으로 변환후 검사)
        grade[0] = String.valueOf(grade1_key); //1학년
        grade[1] = String.valueOf(grade2_key); //2학년
        grade[2] = String.valueOf(grade3_key); //3학년
        grade[3] = String.valueOf(grade4_key); //4학년

        for(int i=0; i<4; i++){
            if(grade[i] == "true"){ //키값이 트루가 된다면
                    if(i == 0){ //1학년일때
                        Grade = 1;
                    } else if(i == 1){ //2학년일때
                        Grade = 2;
                    } else if(i == 2){
                        Grade = 3;
                    } else if(i == 3){
                        Grade = 4;
                    }
            }
        }
        //이 정보만 다음에 같이 전달

        //Write down score
        final EditText write = (EditText) findViewById(R.id.howmuch_writescore);

        //next icon Button
        ImageView Next = (ImageView) findViewById(R.id.howmuch_nextbtn);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //입력 값 체크
                if(write.getText().toString().length() != 0){ //공백이 아닐 때
                    String score = write.getText().toString();
                    if(Integer.parseInt(score) > 21){ //21학점 이상은 불가
                        //경고 알림창 띄우기
                        Toast.makeText(HowMuchActivity.this, "21학점이 최대 학점입니다.\n21학점 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if(Integer.parseInt(score) < 2){ //2학점 이하는 불가
                        //경고 알림창 띄우기
                        Toast.makeText(HowMuchActivity.this, "2학점이 최소 학점입니다.\n2학점 이상으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        setPreferenceInt(write_score, Integer.parseInt(score));//정보 받기
                        Intent intent = new Intent(
                                getApplicationContext(),
                                SelectTimeSetActivity.class);
                        intent.putExtra("Write", getPerferenceInt(write_score)); //정보전송 -> 몇학점인지(int)
                        intent.putExtra("Grade", Grade); //정보 전송 -> 몇학년인지(int)
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                } else { //공백일때
                    Toast.makeText(HowMuchActivity.this, "입력란에 원하는 학점을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void setPreferenceInt(String key, int value){ //학점 점수 저장(int)
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getPerferenceInt(String key) { //데이터 불러오기(확인용) ->integer
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        return pref.getInt(key,0);
    }

    @Override
    public void onBackPressed() { //화면에서 뒤로가기를 눌렀을 때 변수 초기화
        write_score = "write_score";
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}