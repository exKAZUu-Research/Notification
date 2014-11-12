package com.example.notification;

import android.content.SyncStatusObserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Parser {

    //private static File file = new File("text/sample.txt");
    static Scanner sc;
    private static String[] commands = new String[4]; // 状態別のコマンドが格納される
    private static ArrayList<String> oldcommands = new ArrayList<String>(); // 最初に書かれたコマンドがはいっている
    private static int pointer; // ポインター(どこをよんでいるかわかるように)
    private static ArrayList<String> Gcom = new ArrayList<String>();
    private static ArrayList<String> Ccom = new ArrayList<String>();
    private static ArrayList<String> Tcom = new ArrayList<String>();
    private static ArrayList<String> Fcom = new ArrayList<String>();

    public Parser(ArrayList<String> oldcommands, String[] commands, ArrayList<String> Gcom, ArrayList<String> Ccom, ArrayList<String> Tcom, ArrayList<String> Fcom) {
        this.oldcommands = oldcommands;
        this.commands = commands;
        this.Gcom = Gcom;
        this.Ccom = Ccom;
        this.Tcom = Tcom;
        this.Fcom = Fcom;
    }

    public static void main() {
        pointer = 0;

        /*try { // fileから読み込み
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        for (int i = 0; i < 4; i++) {
            commands[i] = "";
        }
        Gcom.clear();
        Ccom.clear();
        Tcom.clear();
        Fcom.clear();

        //readCommand();
        program(15);

        for (int i = 0; i < 4; i++) { // 最後に全部出力して終わり
            System.out.println(commands[i]);
        }

        for (int i = 0; i < 4; i++) {
            commands[i] += "!";
            newParser(commands[i], i);
        }
        System.out.println(Gcom.get(0));
    }

    /**
     * とりあえず全部のコマンドを読み込む
     */
    public static void readCommand() { // とりあえず全部のコマンドを読み込む
        while (sc.hasNext()) {
            oldcommands.add(sc.next());
        }
    }

    public static void program(int mask) {
        while (pointer < oldcommands.size()) {
            command(mask);
        }
    }

    public static void command(int mask) {
        String str = oldcommands.get(pointer);
        pointer++;

        if (str.equals("ON")) {
            LED(mask);
        } else if (str.equals("if")) {
            ifStatement();
        } else if (str.equals("for")) {
            forStatement(mask);
        } else { // エラー処理
            System.err.println("コンパイルエラー");
        }
    }

    public static void LED(int mask) {

        int time = number();

        for (int i = 0; i < 4; i++) {
            int j = 1 << i; // 1をiだけ左にbitシフト
            if ((j & mask) == j) { // bitごとのAND演算
                commands[i] += "ON" + time;
            }
        }

        String str = oldcommands.get(pointer);
        pointer++;

        if (!str.equals("OFF")) { // エラー処理
            System.err.println("OFFがないよ");
            return;
        }

        time = number();

        for (int i = 0; i < 4; i++) {
            int j = 1 << i; // 1をiだけ左にbitシフト
            if ((j & mask) == j) { // bitごとのAND演算
                commands[i] += "OFF" + time;
            }
        }

    }

    public static void ifStatement() {

        String cond = condition();
        int flag = 15;

        if (cond == null) {
            System.err.println("条件がないよ");
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

            if (oldcommands.get(pointer).equals("elseif")) {
                pointer++;
                cond = condition();
            } else if (oldcommands.get(pointer).equals("else")) {
                pointer++;
                command(flag);

                while (!oldcommands.get(pointer).equals("ifend")) {
                    if (oldcommands.get(pointer).equals("elseif")) {
                        System.err.println("else の後に　elseif　はだめだよ");
                        return;
                    }
                    command(flag);
                }
            }
        }

        String str = oldcommands.get(pointer);
        pointer++;
        if (!str.equals("ifend")) {
            System.err.println("ifのendがないよ");
        }
    }

    public static void forStatement(int mask) {

        int num = number();
        int tpointer = pointer;
        for (int i = 0; i < num; i++) {
            pointer = tpointer;
            command(mask);
        }

        String str = oldcommands.get(pointer);
        pointer++;
        if (!str.equals("forend")) {
            System.err.println("forのendがないよ");
        }
    }

    public static int number() {
        String str = oldcommands.get(pointer);
        pointer++;
        return Integer.parseInt(str);
    }

    public static String condition() {
        String str = oldcommands.get(pointer);
        pointer++;

        if (!str.equals("Gmail") && !str.equals("Calender")
                && !str.equals("Twitter") && !str.equals("Facebook")) {
            return null;
        }

        return str;
    }

    public static void newParser(String com, int num) {
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
        newcom.add("!");

        if (num == 0) {
            for (int i = 0; i < newcom.size(); i++) {
                Gcom.add(newcom.get(i));
            }
        } else if (num == 1) {
            for (int i = 0; i < newcom.size(); i++) {
                Ccom.add(newcom.get(i));
            }
        } else if (num == 2) {
            for (int i = 0; i < newcom.size(); i++) {
                Tcom.add(newcom.get(i));
            }
        } else {
            for (int i = 0; i < newcom.size(); i++) {
                Fcom.add(newcom.get(i));
            }
        }
    }

}
