public class NullRef {
    public static void main(String[] args) {
        Dummy dummy = null;
        dummy.doit();
    }
}

class Dummy {
    public void doit() {
    }
}
