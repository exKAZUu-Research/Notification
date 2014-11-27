package com.example.notification.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.ArrayList;

public class SelectActivity extends Activity {

    static final ArrayList<String> file = new ArrayList<String>();
    static String LongClickItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        file.clear();
        //保存されているファイルを取得する
        String message = "";
        String fileName = "number.text";

        String s = null;
        try {
            InputStream in = openFileInput("number.text");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in, "UTF-8"));
            while ((s = reader.readLine()) != null) {
                file.add(s);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //保存されているファイルのリストを追加
        for (int i = 0; i < file.size(); i++) {
            adapter.add(file.get(i));
        }

        //最後に新しいプログラムを作れるようにnewを追加
        adapter.add("new");
        ListView listView = (ListView) findViewById(R.id.list);

        // アダプターを設定します
        listView.setAdapter(adapter);

        ListView lv = (ListView) findViewById(R.id.list);

        //リストの項目を押したときの処理
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得
                final String item = (String) listView.getItemAtPosition(position);
                //int i = 1;
                if (item.equals("new")) {
                    LayoutInflater inflater = LayoutInflater.from(SelectActivity.this);
                    View textview = inflater.inflate(R.layout.dialog, null);
                    final EditText editText = (EditText) textview.findViewById(R.id.editText);

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(SelectActivity.this);
                    builder2.setTitle("名前をつけよう");
                    builder2.setView(textview);
                    builder2.setPositiveButton("作成",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("TAG", "input text = " + editText.getText().toString());
                                    if (!file.contains(editText.getText().toString())) {
                                        String fileName = "number.text";
                                        file.add(editText.getText().toString());
                                        String inputText = "";
                                        for (int i = 0; i < file.size(); i++) {
                                            inputText += file.get(i);
                                            inputText += "\n";
                                        }
                                        String message = "";
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
                                        finish();
                                        Intent intent = new Intent(SelectActivity.this, SelectActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast toast = Toast.makeText(SelectActivity.this, R.string.makefile, Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                    }
                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                    intent.putExtra("name", item);
                    startActivity(intent);
                }
            }
        });

        //リストの項目を長押ししたときの処理
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                final String item = (String) listView.getItemAtPosition(position);
                LongClickItem = item;
                if (!item.equals("new")) {
                    AlertDialogFragment dialog = new AlertDialogFragment();
                    dialog.show(getFragmentManager(), "dialog");
                }
                return false;
            }
        });
    }

    //長押ししたときに表示されるアラートダイアログの処理
    public class AlertDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("削除");
            builder.setMessage("このプログラムを削除しますか？");

            builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    int point = file.indexOf(LongClickItem);
                    file.remove(point);

                    String fileName = "number.text";
                    String inputText = "";
                    for (int i = 0; i < file.size(); i++) {
                        inputText += file.get(i);
                        inputText += "\n";
                    }
                    String message = "";
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
                    finish();
                    Intent intent = new Intent(SelectActivity.this, SelectActivity.class);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            return builder.create();
        }
    }
}
