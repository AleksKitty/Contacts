package contacts;

import java.io.*;
import java.util.List;

class SerializationUtils {
    /**
     * Serialize the given object to the file
     */
    public static void serialize(List<Contact> contacts, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(contacts);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    public static List<Contact> deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return (List<Contact>) obj;
    }
}
