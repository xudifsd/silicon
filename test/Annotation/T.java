@AnnotationClass
public class T {
    @MethodFieldAnnotation
    @AnnotationField
    public int i;

    @AnnotationMultFields(
            sWithDefaultValue={"s1", "s2"},
            version=2)
    public String s;

    @AnnotationFieldWithValue(
        sWithDefaultValue="specified String value",
        version=20
    )
    public double d;
        
    @AnnotationFieldWithOutDefault(
        sWithOutDefaultValue="wihtout default",
        version=20
    )
    public float c;
    
    @AnnotationField
    @AnnotationFieldWithValue(version=30)
    public float fieldWithTwoAnnotation;
    public static void main(String[] args) {
        System.out.println("Hello carbon");
    }

    @MethodFieldAnnotation
    @InProgressWithClassVisibility
    public static void foo() {
    }

    @InProgressWithRuntimeVisibility
    public void bar() {
    }
}
