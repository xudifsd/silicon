public class Target {
    public static void main(String[] args) {
        int x = 10;
        int y = 1;
        if (x < 90 && y > 2) {
            test(20);
        } else {
            test(120);
        }
    }
    public static void test(int x) {
        if (x > 30) {
            System.out.println("error!");
        } else {
            target();
        }
    }
    public static void target() {
    }
}
