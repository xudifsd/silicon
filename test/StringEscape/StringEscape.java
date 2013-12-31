public class StringEscape {
    public static void main(String[] args) {
        System.out.println("a\nb");
        System.out.println("a\rb");
        System.out.println("a\tb");
        System.out.println("a\bb");
        System.out.println("a\0b");
        System.out.println("a\'b'c");
        System.out.println("a\"b'c");
        System.out.println("a\\b'c");
    }
}
