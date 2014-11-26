package com.example.notification.function;

import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    static Scanner sc;
    private String[] commands = new String[4]; // 状態別のコマンドが格納される
    private ArrayList<String> oldcommands = new ArrayList<String>(); // 最初に書かれたコマンドがはいっている
    private int pointer; // ポインター(どこをよんでいるかわかるように)
    private ArrayList<String> Gcom = new ArrayList<String>();    //音楽を鳴らすときにわかりやすくするためのコマンド
    private ArrayList<String> Ccom = new ArrayList<String>();
    private ArrayList<String> Tcom = new ArrayList<String>();
    private ArrayList<String> Fcom = new ArrayList<String>();

    private boolean err;

    public Parser(ArrayList<String> oldcommands, String[] commands, ArrayList<String> Gcom, ArrayList<String> Ccom, ArrayList<String> Tcom, ArrayList<String> Fcom) {
        this.oldcommands = oldcommands;
        this.commands = commands;
        this.Gcom = Gcom;
        this.Ccom = Ccom;
        this.Tcom = Tcom;
        this.Fcom = Fcom;
        this.err = false;
    }

    public void main() {
        pointer = 0;

        for (int i = 0; i < 4; i++) {
            commands[i] = "";
        }
        Gcom.clear();
        Ccom.clear();
        Tcom.clear();
        Fcom.clear();

        program(15);

        for (int i = 0; i < 4; i++) { // 最後に全部出力して終わり
            System.out.println(commands[i]);
        }

        for (int i = 0; i < 4; i++) {
            commands[i] += "!";
            newParser2(commands[i], i);
        }
    }

    /**
     * とりあえず全部のコマンドを読み込む
     */
    public void readCommand() { // とりあえず全部のコマンドを読み込む
        while (sc.hasNext()) {
            oldcommands.add(sc.next());
        }
    }

    public void program(int mask) {
        while (pointer < oldcommands.size()) {
            command(mask);
        }
    }

    public void command(int mask) {
        String str = oldcommands.get(pointer);
        pointer++;

        if (str.equals("ON") || str.equals("OFF") || str.equals("fadeIn")
                || str.equals("fadeOut")) {
            pointer--;
            LED_new(mask);
        } else if (str.equals("if")) {
            ifStatement();
        } else if (str.equals("for")) {
            forStatement(mask);
        } else { // エラー処理
            System.err.println("コンパイルエラー");
            this.err = true;
        }
    }

    public void LED_new(int mask) {

        String str = oldcommands.get(pointer);
        pointer++;
        while ((str.equals("ON") || str.equals("OFF") || str.equals("fadeIn")
                || str.equals("fadeOut")) && pointer < oldcommands.size()) {
            for (int i = 0; i < 4; i++) {
                int j = 1 << i; // 1をiだけ左にbitシフト
                if ((j & mask) == j) { // bitごとのAND演算
                    commands[i] += str;
                }
            }
            str = oldcommands.get(pointer);
            pointer++;
        }
        pointer--;
    }

    public void ifStatement() {


        String cond = condition();
        int flag = 15;

        if (cond == null) {
            System.err.println("条件がないよ");
            this.err = true;
            return;
        }
        while (!oldcommands.get(pointer).equals("ifend")) {

            if (cond.equals("Gmail")) {
                command(1);
                flag -= 1;
            } else if (cond.equals("Calendar")) {
                command(2);
                flag -= 2;
            } else if (cond.equals("Twitter")) {
                command(4);
                flag -= 4;
            } else {
                command(8);
                flag -= 8;
            }

            if (pointer >= oldcommands.size()) {
                System.err.println("ifのendがないよ");
                this.err = true;
                return;
            }
            if (oldcommands.get(pointer).equals("elseif")) {
                pointer++;
                cond = condition();
            } else if (oldcommands.get(pointer).equals("else")) {
                pointer++;
                command(flag);

                if (!oldcommands.get(pointer).equals("ifend")) {
                    System.err.println("elseの後にはifendが必要です");
                    this.err = true;
                    return;
                }
            } else if (!oldcommands.get(pointer).equals("ifend")) {
                System.err.println("ifのendがないよ");
                this.err = true;
                return;
            }
        }
        pointer++;
    }

    public void forStatement(int mask) {

        if (!Character.isDigit(oldcommands.get(pointer).charAt(0))) {
            System.err.println("forの後には数字をいれてね");
            this.err = true;
            return;
        }
        int num = number();
        int tpointer = pointer;
        for (int i = 0; i < num; i++) {
            pointer = tpointer;
            command(mask);
        }

        if (pointer >= oldcommands.size()) {
            System.err.println("forのendがないよ");
            this.err = true;
            return;
        }
        String str = oldcommands.get(pointer);
        pointer++;
        if (!str.equals("forend")) {
            System.err.println("forのendがないよpart2");
            this.err = true;
        }
    }

    public int number() {
        String str = oldcommands.get(pointer);
        pointer++;
        return Integer.parseInt(str);
    }

    public String condition() {
        String str = oldcommands.get(pointer);
        pointer++;

        if (!str.equals("Gmail") && !str.equals("Calendar")
                && !str.equals("Twitter") && !str.equals("Facebook")) {
            return null;
        }

        return str;
    }

    public void newParser(String com, int num) {     //音楽をならすのをわかりやすくするための解析
        sc = new Scanner(com);
        ArrayList<String> newcom = new ArrayList<String>();
        String str = sc.next();
        int pointer = 0;
        while (str.charAt(pointer) != '!') {
            pointer += 2;
            int time = Integer.parseInt("" + str.charAt(pointer));
            for (int i = 0; i < time; i++) {
                newcom.add("ON");
            }
            pointer += 4;
            time = Integer.parseInt("" + str.charAt(pointer));
            for (int i = 0; i < time; i++) {
                newcom.add("OFF");
            }
            pointer += 1;
        }

        for (int i = 0; i < newcom.size(); i++) {
            if (num == 0) {
                Gcom.add(newcom.get(i));
            } else if (num == 1) {
                Ccom.add(newcom.get(i));
            } else if (num == 2) {
                Tcom.add(newcom.get(i));
            } else {
                Fcom.add(newcom.get(i));
            }
        }
    }

    public void newParser2(String com, int num) {
        sc = new Scanner(com);
        ArrayList<String> newcom = new ArrayList<String>();
        String str = sc.next();
        int pointer = 0;
        while (str.charAt(pointer) != '!') {
            if (str.charAt(pointer) == 'O') {
                pointer++;
                if (str.charAt(pointer) == 'N') {
                    newcom.add("ON");
                    pointer++;
                } else {
                    newcom.add("OFF");
                    pointer += 2;
                }
            } else if (str.charAt(pointer) == 'f') {
                pointer += 4;
                if (str.charAt(pointer) == 'I') {
                    newcom.add("fadeIn");
                    pointer += 2;
                } else {
                    newcom.add("fadeOut");
                    pointer += 3;
                }
            }
        }

        for (int i = 0; i < newcom.size(); i++) {
            if (num == 0) {
                Gcom.add(newcom.get(i));
            } else if (num == 1) {
                Ccom.add(newcom.get(i));
            } else if (num == 2) {
                Tcom.add(newcom.get(i));
            } else {
                Fcom.add(newcom.get(i));
            }
        }
    }

    //コンパイルエラーかどうか
    public boolean isErr() {
        return err;
    }
}
