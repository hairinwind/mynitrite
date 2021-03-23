package my.nitrite;

import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.h2.store.fs.FileUtils;

import java.util.ArrayList;
import java.util.Date;

import static org.dizitart.no2.Document.createDocument;

/**
 * Hello Nitrite!
 *
 */
public class App 
{

    public static final String TEST_DB = "/tmp/test.db";

    public static void main(String[] args )
    {
        FileUtils.delete(TEST_DB);

        Nitrite db = getDb();

        NitriteCollection collection = db.getCollection("test");
        // create a document to populate data
        Document doc = createDocument("firstName", "John")
                .put("lastName", "Doe")
                .put("birthDay", new Date())
                .put("data", new byte[] {1, 2, 3})
                .put("fruits", new ArrayList<String>() {{ add("apple"); add("orange"); }})
                .put("note", "a quick brown fox jump over the lazy dog");

        // insert the document
        collection.insert(doc);
        db.commit();

        System.out.println("after insert:" + FileUtils.size(TEST_DB));

        collection.remove(doc);

        Document doc1 = collection.getById(doc.getId());
        System.out.println("doc1: " + doc1);

        db.commit();
        db.close();

        System.out.println("after remove:" + FileUtils.size(TEST_DB));
    }

    private static Nitrite getDb() {
        return Nitrite.builder()
                .compressed()
                .filePath(TEST_DB)
                .openOrCreate("user", "password");
    }


}
