public class IndexOOB {
    public static void main(String[] args) {
        test(10);
    }
    public static void test(int x) {
        int[] array = new int[x];
        if (x > 10) {
            System.out.println("error!");
        } else {
            array[x] = x;
        }
    }
}
