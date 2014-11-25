package com.example.notification.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notification.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SelectActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //ファイルに作ったプログラムがいくつ保存されているかをかいておく
        String message = "";
        String fileName = "number.text";
        String str = null;
        try {
            InputStream in = openFileInput("number.text");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String s;
            while ((s = reader.readLine()) != null) {
                str = s;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (str == null) {
            String inputText = "";
            inputText += 0;
            try {
                FileOutputStream outStream = openFileOutput(fileName, MODE_PRIVATE);
                OutputStreamWriter writer = new OutputStreamWriter(outStream);
                writer.write(inputText);
                writer.flush();
                writer.close();
                message = "File saved.";
            } catch (FileNotFoundException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                message = e.getMessage();
                e.printStackTrace();
            }
        }

        String number = "";     //いくつプログラムが保存されているか(最初は0個)
        number = str;

        // アイテムを追加します
        //保存されている個数分だけプログラムのリストを追加
        for (int i = 0; i < Integer.parseInt(number); i++) {
            adapter.add("Sample" + (i + 1));
        }

        //最後に新しいプログラムを作れるようにnewを追加
        adapter.add("new");
        ListView listView = (ListView) findViewById(R.id.list);
        // アダプターを設定します
        listView.setAdapter(adapter);

        ListView lv = (ListView) findViewById(R.id.list);
        final String finalNumber = number;      //いくつのプログラムが保存されているか
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                String item = (String) listView.getItemAtPosition(position);
                int i = 1;
                if (item.equals("new")) {
                    Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                    intent.putExtra("num", item);
                    intent.putExtra("number", finalNumber);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                    intent.putExtra("num", item);
                    intent.putExtra("number", finalNumber);
                    startActivity(intent);
                }
            }
        });
    }
}