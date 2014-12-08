package com.example.notification.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.notification.function.Parser;

import java.util.ArrayList;

public class SelectActivity extends Activity {

    static final ArrayList<String> file = new ArrayList<String>();
    static String LongClickItem;
    static String ClickItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.list);

        //データベース
        String str = "data/data/" + getPackageName() + "/Sample.db";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);

        String qry1 = "CREATE TABLE product" + "(id INTEGER PRIMARY KEY, name STRING)";
        String qry3 = "SELECT * FROM product";

        //テーブルの作成
        try {
            db.execSQL(qry1);
        } catch (SQLException e) {
            Log.e("ERROR", e.toString());
        }

        //データの検索
        Cursor cr = db.rawQuery(qry3, null);
        startManagingCursor(cr);

        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        while (cr.moveToNext()) {
            int i = cr.getColumnIndex("id");
            int n = cr.getColumnIndex("name");
            int id = cr.getInt(i);
            String name = cr.getString(n);

            adapter.add(name);
            file.add(name);
        }
        //db.close();

        // アダプターを設定します
        listView.setAdapter(adapter);
        ListView lv = (ListView) findViewById(R.id.list);

        //最後に新しいプログラムを作れるようにnewを追加
        adapter.add("new");

        //リストの項目を押したときの処理
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得
                final String item = (String) listView.getItemAtPosition(position);
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
                                        String fileName = editText.getText().toString();
                                        file.add(fileName);

                                        String str = "data/data/" + getPackageName() + "/Sample.db";
                                        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
                                        String qry = "INSERT INTO product(name) VALUES('" + fileName + "')";
                                        db.execSQL(qry);
                                        //db.close();

                                        Intent intent = new Intent(SelectActivity.this, SelectActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast toast = Toast.makeText(SelectActivity.this, "名前が重複しています", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                    }
                                }
                            })
                            .show();
                } else {
                    ClickItem = (String) listView.getItemAtPosition(position);
                    AlertDialogFragment dialog = new AlertDialogFragment();
                    dialog.show(getFragmentManager(), "dialog");
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
                    LongAlertDialogFragment dialog = new LongAlertDialogFragment();
                    dialog.show(getFragmentManager(), "dialog");
                }
                return true;
            }
        });
    }

    //リストを押したときに出るアラートダイアログの中身
    public class AlertDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(ClickItem);

            builder.setPositiveButton("編集", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                    intent.putExtra("name", ClickItem);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("メインに設定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    ArrayList<String> Gcom = new ArrayList<String>();
                    ArrayList<String> Ccom = new ArrayList<String>();
                    ArrayList<String> Tcom = new ArrayList<String>();
                    ArrayList<String> Fcom = new ArrayList<String>();
                    String[] commands = new String[4];
                    ArrayList<String> oldcommands = new ArrayList<String>();
                    String[][] program = new String[2][12];

                    //データベースから読み込む
                    String str = "data/data/" + getPackageName() + "/Sample.db";
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);

                    String qry1 = "CREATE TABLE " + ClickItem + " (id INTEGER PRIMARY KEY, text STRING)";
                    String qry3 = "SELECT * FROM " + ClickItem;

                    //テーブルの作成
                    try {
                        db.execSQL(qry1);
                    } catch (SQLException e) {
                        Log.e("ERROR", e.toString());
                    }

                    //データの検索
                    Cursor cr = db.rawQuery(qry3, null);
                    startManagingCursor(cr);

                    int x = 0;
                    int y = 0;
                    while (cr.moveToNext()) {
                        int t = cr.getColumnIndex("text");
                        String text = cr.getString(t);
                        if (text.equals("")) {
                            program[x][y] = "";
                        } else {
                            program[x][y] = text;
                        }
                        x++;
                        y++;
                        if (x == 2) {
                            x = 0;
                        } else {
                            y--;
                        }
                    }

                    for (int j = 0; j < 12; j++) {
                        for (int i = 0; i < 2; i++) {
                            if (program[i][j] != "") {
                                oldcommands.add(program[i][j]);
                            }
                        }
                    }
                    //解析
                    Parser parser = new Parser(oldcommands, commands, Gcom, Ccom, Tcom, Fcom);
                    parser.main();

                    //データベースから読み込む

                    String qryG = "CREATE TABLE Gcom (id INTEGER PRIMARY KEY, text STRING)";
                    String qryC = "CREATE TABLE Ccom (id INTEGER PRIMARY KEY, text STRING)";
                    String qryT = "CREATE TABLE Tcom (id INTEGER PRIMARY KEY, text STRING)";
                    String qryF = "CREATE TABLE Fcom (id INTEGER PRIMARY KEY, text STRING)";

                    //テーブルの作成
                    try {
                        db.execSQL(qryG);
                    } catch (SQLException e) {
                        Log.e("ERROR", e.toString());
                    }
                    try {
                        db.execSQL(qryC);
                    } catch (SQLException e) {
                        Log.e("ERROR", e.toString());
                    }
                    try {
                        db.execSQL(qryT);
                    } catch (SQLException e) {
                        Log.e("ERROR", e.toString());
                    }
                    try {
                        db.execSQL(qryF);
                    } catch (SQLException e) {
                        Log.e("ERROR", e.toString());
                    }

                    String delete = "DELETE FROM Gcom";
                    db.execSQL(delete);
                    delete = "DELETE FROM Ccom";
                    db.execSQL(delete);
                    delete = "DELETE FROM Tcom";
                    db.execSQL(delete);
                    delete = "DELETE FROM Fcom";
                    db.execSQL(delete);

                    String qry;
                    for (int i = 0; i < Gcom.size(); i++) {
                        qry =  "INSERT INTO Gcom(text) VALUES('" + Gcom.get(i) + "')";
                        db.execSQL(qry);
                    }
                    for (int i = 0; i < Ccom.size(); i++) {
                        qry =  "INSERT INTO Ccom(text) VALUES('" + Ccom.get(i) + "')";
                        db.execSQL(qry);
                    }
                    for (int i = 0; i < Tcom.size(); i++) {
                        qry =  "INSERT INTO Tcom(text) VALUES('" + Tcom.get(i) + "')";
                        db.execSQL(qry);
                    }
                    for (int i = 0; i < Fcom.size(); i++) {
                        qry =  "INSERT INTO Fcom(text) VALUES('" + Fcom.get(i) + "')";
                        db.execSQL(qry);
                    }
                    //db.close();
                }
            });
            return builder.create();
        }
    }

    //長押ししたときに表示されるアラートダイアログの処理
    public class LongAlertDialogFragment extends DialogFragment {
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

                    String str = "data/data/" + getPackageName() + "/Sample.db";
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
                    String qry = "DELETE FROM product WHERE name='" + LongClickItem + "'";
                    db.execSQL(qry);
                    //db.close();

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
