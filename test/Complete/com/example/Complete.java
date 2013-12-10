package com.example;

public class Complete {
    public int i;
    private String s;

    public Complete(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public void throwsEx() throws Exception {
        if (i == 1)
            throw new Exception("i is 1");
    }

    public int withWhile() {
        int sum = 0;
        int i = 0;
        while (i < 10)
            sum += i;
        return sum;
    }

    public int withSwitch() {
        int sum = 0;
        switch (i) {
            case 1:
                return i;
            case 2:
                return i * 100;
            case 3:
                sum = i + 1;
            /* fall through */
            case 4:
                sum = i + 2;
                break;
            default:
                return i;
        }
        return sum;
    }
}
