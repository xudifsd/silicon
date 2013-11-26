public class Fac {
    public static int compute(int num) {
        if (num > 0)
            return num * compute(num - 1);
        else
            return 1;
    }
    public static void main(String[] args) {
        System.out.println(compute(10));
    }
}
