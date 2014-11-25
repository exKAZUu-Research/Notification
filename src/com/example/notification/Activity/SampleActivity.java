package com.example.notification.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.notification.R;
import com.example.notification.function.TempDataUtil;
import com.example.notification.function.ViewDto;


public class SampleActivity extends Activity {
    /**
     * 画面のデータを保持するDto
     */
    private ViewDto dto;

    /**
     * 「名前」欄
     */
    private EditText editTextName;

    /**
     * 「電話番号」欄
     */
    private EditText editTextTel;

    /**
     * 「メール」欄
     */
    private EditText editTextMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 画面の要素を取得
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextTel = (EditText)findViewById(R.id.editTextTel);
        editTextMail = (EditText)findViewById(R.id.editTextMail);

        // 画面のデータを読み込む
        load();
    }

    /**
     * 画面のボタンがタップされた時の処理
     * @param view
     */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnStore:
                // ボタン「保存」がタップされたとき
                store();
                break;

            case R.id.btnClear:
                // ボタン「クリア」がタップされたとき
                clear();
                break;

            case R.id.btnLoad:
                // ボタン「読込」がタップされたとき
                load();
                break;
        }
    }

    /**
     * データを書き込む
     */
    private void store(){
        // 画面の情報をDtoに詰め込む
        dto.setName(editTextName.getText().toString());
        dto.setTel(editTextTel.getText().toString());
        dto.setMail(editTextMail.getText().toString());

        // Dtoに詰め込んだデータをファイルに保存する
        TempDataUtil.store(this, dto);
    }

    /**
     * 画面の入力欄をすべてクリアする
     */
    private void clear(){
        dto.setName("");
        dto.setTel("");
        dto.setMail("");

        setDtoToView();
    }

    /**
     * データを読み込む
     */
    private void load(){
        // ファイルに保存したデータを読み込む
        dto = (ViewDto) TempDataUtil.load(this);

        // 読み込んだデータを画面に反映する
        setDtoToView();
    }

    /**
     * 画面のデータを画面に反映する
     */
    private void setDtoToView(){
        editTextName.setText(dto.getName());
        editTextTel.setText(dto.getTel());
        editTextMail.setText(dto.getMail());
    }
}