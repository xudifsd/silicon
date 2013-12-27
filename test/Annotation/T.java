public class T {
    @AnnotationField
    public int i;

    @AnnotationFieldWithValue(
        sWithDefaultValue="specified String value",
        version=20
    )
    public double d;

    @AnnotationField
    @AnnotationFieldWithValue(version=30)
    public float fieldWithTwoAnnotation;
    public static void main(String[] args) {
        System.out.println("Hello carbon");
    }

    @InProgressWithClassVisibility
    public static void foo() {
    }

    @InProgressWithRuntimeVisibility
    public void bar() {
    }
}
