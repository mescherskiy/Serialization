import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SomeClass {
    @Save
    private static String firstName = "Ivan";
    @Save
    private static String lastName = "Ivanov";
    @Save
    private static Integer age = 30;
    private static Integer weight = 85;
    private static Double height = 1.85;

    public SomeClass() {
    }

    public void serialize(File file, Field field) throws IOException, IllegalAccessException {
        FileWriter fw = new FileWriter(file, true);
        field.setAccessible(true);
        String fieldName = field.getName();
        fw.write(fieldName + "=" + field.get(null));
        fw.write(System.lineSeparator());
        fw.flush();
    }

    public void changeAnnotatedFields () {
        firstName = "Petr";
        lastName = "Petrov";
        age = 27;
    }

    public void deserialize(File file, Class<?> cls) throws IOException, IllegalAccessException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        Field[] fields = cls.getDeclaredFields();
        Map<String, String> fieldsMap = new HashMap<>();
        String line = br.readLine();
        while (line != null) {
            fieldsMap.put(line.substring(0, line.indexOf("=")), line.substring(line.indexOf("=") + 1, line.length()));
            line = br.readLine();
        }
        for (Field field : fields) {
            if (fieldsMap.containsKey(field.getName())) {
                field.setAccessible(true);
                if (field.getType() == Integer.class) {
                    field.set(null, Integer.parseInt(fieldsMap.get(field.getName())));
                } else {
                    field.set(null, fieldsMap.get(field.getName()));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "SomeClass{firstName = " + firstName + ", lastName = " + lastName + ", age = " + age + ", weight = " + weight + ", height = " + height + "}";
    }
}
