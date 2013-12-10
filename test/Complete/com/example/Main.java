package com.example;

public class Main {
    public static void main(String[] args) {
        Complete com = new Complete(1, "Hello world");
        try {
            com.throwsEx();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(com.withWhile());
        System.out.println(com.withSwitch());
    }
}
