import java.io.*;
import java.lang.reflect.Field;

public class Serialization {
    public static void main(String[] args) throws IOException {

        File file = new File("test.txt");
        SomeClass sc = new SomeClass();
        Class<?> cls = sc.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Save.class)) {
                try {
                    sc.serialize(file, field);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
// Меняем значения полей, чтобы убедиться, что десериализация сработала
        sc.changeAnnotatedFields();
        System.out.println(sc.toString());

        try {
            sc.deserialize(file, cls);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sc.toString());

    }
}
