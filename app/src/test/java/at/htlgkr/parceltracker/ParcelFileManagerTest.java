package at.htlgkr.parceltracker;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ParcelFileManagerTest {

    @Test
    public void parcelFileManager_load_something_simple() throws IOException {
        String initialString = TEST_FILE_SIMPLE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        ParcelFileManager underTest = new ParcelFileManager();
        List<Parcel> parcels = underTest.loadParcels(targetStream, getAllCategories());

        assertTrue(parcels.size() > 0);
    }

    @Test
    public void parcelFileManager_load_correctSize_simple() throws IOException {
        String initialString = TEST_FILE_SIMPLE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        ParcelFileManager underTest = new ParcelFileManager();
        List<Parcel> parcels = underTest.loadParcels(targetStream, getAllCategories());

        assertEquals(1, parcels.size());
    }

    @Test
    public void parcelFileManager_load_correctData_simple() throws IOException {
        String initialString = TEST_FILE_SIMPLE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        ParcelFileManager underTest = new ParcelFileManager();
        List<Parcel> parcels = underTest.loadParcels(targetStream, getAllCategories());

        TestUtils.ListTester<Parcel> lt = new TestUtils.ListTester();

        assertTrue(lt.listsEqual(parcels, getAllParcels().subList(0, 1)));
    }

    @Test
    public void parcelFileManager_load_something() throws IOException {
        String initialString = TEST_FILE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        ParcelFileManager underTest = new ParcelFileManager();
        List<Parcel> parcels = underTest.loadParcels(targetStream, getAllCategories());

        assertTrue(parcels.size() > 0);
    }

    @Test
    public void parcelFileManager_load_correctSize() throws IOException {
        String initialString = TEST_FILE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        ParcelFileManager underTest = new ParcelFileManager();
        List<Parcel> parcels = underTest.loadParcels(targetStream, getAllCategories());

        assertEquals(10, parcels.size());
    }

    @Test
    public void parcelFileManager_load_correctData() throws IOException {
        String initialString = TEST_FILE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        ParcelFileManager underTest = new ParcelFileManager();
        List<Parcel> parcels = underTest.loadParcels(targetStream, getAllCategories());

        TestUtils.ListTester<Parcel> lt = new TestUtils.ListTester();

        assertTrue(lt.listsEqual(parcels, getAllParcels()));
    }

    @Test
    public void parcelFileManager_save_writesSomething() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ParcelFileManager underTest = new ParcelFileManager();
        underTest.saveParcels(os, getAllParcels().subList(0, 1));
        String result = new String(os.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);

        assertTrue(result.length() > 5);
    }

    @Test
    public void parcelFileManager_save_correctDatasetCount() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ParcelFileManager underTest = new ParcelFileManager();
        underTest.saveParcels(os, getAllParcels());
        String result = new String(os.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);

        assertEquals(10, countOccurances(result, "<dataset>"));
        assertEquals(10, countOccurances(result, "</dataset>"));
    }

    @Test
    public void parcelFileManager_save_correctDescCount() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ParcelFileManager underTest = new ParcelFileManager();
        underTest.saveParcels(os, getAllParcels());
        String result = new String(os.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);

        assertEquals(10, countOccurances(result, "<desc>"));
        assertEquals(10, countOccurances(result, "</desc>"));
    }

    @Test
    public void parcelFileManager_save_correctData() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ParcelFileManager underTest = new ParcelFileManager();
        underTest.saveParcels(os, getAllParcels());
        String result = new String(os.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);

        String[] checkData = STORE_TEST.split("\n");

        for(String cData : checkData) {
            assertTrue(result.contains(cData));
        }
    }

    private int countOccurances(String str, String findStr) {
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){
            lastIndex = str.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count ++;
                lastIndex += findStr.length();
            }
        }
        return count;
    }

    public List<Category> getAllCategories() {
        List<Category> result = new ArrayList<>();

        Category cat1 = new Category(1, "Shipping Notice Received");
        Category cat2 = new Category(2, "In Transit");
        Category cat3 = new Category(3, "Delivered");
        Category cat4 = new Category(4, "Expired");

        result.add(cat1);
        result.add(cat2);
        result.add(cat3);
        result.add(cat4);

        return result;
    }

    public List<Parcel> getAllParcels() {
        Category c0 = new Category(1, "Shipping Notice Received");
        LocalDateTime s0 = LocalDateTime.parse("2020-01-20T12:12");
        LocalDateTime e0 = LocalDateTime.parse("2020-01-21T14:14");
        Parcel t0 = new Parcel("Paket P1287", "Versand nach Deutschland. Achtung zerbrechlich.", s0, e0, c0);
        Category c1 = new Category(1, "Shipping Notice Received");
        LocalDateTime s1 = LocalDateTime.parse("2020-01-22T12:12");
        LocalDateTime e1 = LocalDateTime.parse("2020-01-23T00:14");
        Parcel t1 = new Parcel("Amazon Paket P45690", "Kostenloser Versand nach Österreich.", s1, e1, c1);
        Category c2 = new Category(1, "Shipping Notice Received");
        LocalDateTime s2 = LocalDateTime.parse("2020-01-24T04:32");
        LocalDateTime e2 = LocalDateTime.parse("2020-01-25T23:14");
        Parcel t2 = new Parcel("Zalando P56783", "Versand nach Frankreich. Unterschrift nicht vergessen.", s2, e2, c2);
        Category c3 = new Category(2, "In Transit");
        LocalDateTime s3 = LocalDateTime.parse("2020-01-24T04:32");
        LocalDateTime e3 = LocalDateTime.parse("2020-01-25T23:14");
        Parcel t3 = new Parcel("Privates Paket P49234", "Private Lieferung nach Deutschland. Achtung kann Spuren von Lebensmitteln enthalten.", s3, e3, c3);
        Category c4 = new Category(2, "In Transit");
        LocalDateTime s4 = LocalDateTime.parse("2020-01-29T08:22");
        LocalDateTime e4 = LocalDateTime.parse("2020-01-29T09:14");
        Parcel t4 = new Parcel("RSa Brief B88342", "Behördliches Dokument. Ausweispflicht.", s4, e4, c4);
        Category c5 = new Category(3, "Delivered");
        LocalDateTime s5 = LocalDateTime.parse("2020-01-29T08:22");
        LocalDateTime e5 = LocalDateTime.parse("2020-01-29T09:14");
        Parcel t5 = new Parcel("OBI Paket P107045", "Lieferung nach Österreich. Achtung über 3m. Vor Nässe schützen.", s5, e5, c5);
        Category c6 = new Category(4, "Expired");
        LocalDateTime s6 = LocalDateTime.parse("2020-01-01T20:22");
        LocalDateTime e6 = LocalDateTime.parse("2020-01-01T21:14");
        Parcel t6 = new Parcel("Rsb Brief B451113", "Behördliches Dokument. Streng vertraulich.", s6, e6, c6);
        Category c7 = new Category(4, "Expired");
        LocalDateTime s7 = LocalDateTime.parse("2019-12-30T08:22");
        LocalDateTime e7 = LocalDateTime.parse("2019-12-31T09:14");
        Parcel t7 = new Parcel("Übersee Paket P344455", "Lieferung nach Japan. Achtung Zoll.", s7, e7, c7);
        Category c8 = new Category(4, "Expired");
        LocalDateTime s8 = LocalDateTime.parse("2020-12-29T08:22");
        LocalDateTime e8 = LocalDateTime.parse("2020-12-29T09:14");
        Parcel t8 = new Parcel("IKEA Paket P32190", "Lieferung nach Deutschland. Zerbrechlich.", s8, e8, c8);
        Category c9 = new Category(4, "Expired");
        LocalDateTime s9 = LocalDateTime.parse("2020-12-22T08:22");
        LocalDateTime e9 = LocalDateTime.parse("2020-12-31T09:14");
        Parcel t9 = new Parcel("BIPA Paket P000478", "Lieferung nach Italien. Entflammbare Stoffe.", s9, e9, c9);

        List<Parcel> result = new ArrayList<>();

        result.add(t0);
        result.add(t1);
        result.add(t2);
        result.add(t3);
        result.add(t4);
        result.add(t5);
        result.add(t6);
        result.add(t7);
        result.add(t8);
        result.add(t9);

        return result;
    }

    public static final String STORE_TEST = "title=Paket P1287\n" +
            "Versand nach Deutschland. Achtung zerbrechlich.\n" +
            "start=20.01.2020 12:12\n" +
            "end=21.01.2020 14:14\n" +
            "category_id=1\n" +
            "title=Amazon Paket P45690\n" +
            "Kostenloser Versand nach Österreich.\n" +
            "start=22.01.2020 12:12\n" +
            "end=23.01.2020 00:14\n" +
            "category_id=1\n" +
            "title=Zalando P56783\n" +
            "Versand nach Frankreich. Unterschrift nicht vergessen.\n" +
            "start=24.01.2020 04:32\n" +
            "end=25.01.2020 23:14\n" +
            "category_id=1\n" +
            "title=Privates Paket P49234\n" +
            "Private Lieferung nach Deutschland. Achtung kann Spuren von Lebensmitteln enthalten.\n" +
            "start=24.01.2020 04:32\n" +
            "end=25.01.2020 23:14\n" +
            "category_id=2\n" +
            "title=RSa Brief B88342\n" +
            "Behördliches Dokument. Ausweispflicht.\n" +
            "start=29.01.2020 08:22\n" +
            "end=29.01.2020 09:14\n" +
            "category_id=2\n" +
            "title=OBI Paket P107045\n" +
            "Lieferung nach Österreich. Achtung über 3m. Vor Nässe schützen.\n" +
            "start=29.01.2020 08:22\n" +
            "end=29.01.2020 09:14\n" +
            "category_id=3\n" +
            "title=Rsb Brief B451113\n" +
            "Behördliches Dokument. Streng vertraulich.\n" +
            "start=01.01.2020 20:22\n" +
            "end=01.01.2020 21:14\n" +
            "category_id=4\n" +
            "title=Übersee Paket P344455\n" +
            "Lieferung nach Japan. Achtung Zoll.\n" +
            "start=30.12.2019 08:22\n" +
            "end=31.12.2019 09:14\n" +
            "category_id=4\n" +
            "title=IKEA Paket P32190\n" +
            "Lieferung nach Deutschland. Zerbrechlich.\n" +
            "start=29.12.2020 08:22\n" +
            "end=29.12.2020 09:14\n" +
            "category_id=4\n" +
            "title=BIPA Paket P000478\n" +
            "Lieferung nach Italien. Entflammbare Stoffe.\n" +
            "start=22.12.2020 08:22\n" +
            "end=31.12.2020 09:14\n" +
            "category_id=4";

    public static final String TEST_FILE = "<dataset>\n" +
            "   title=Paket P1287\n" +
            "   <desc>\n" +
            "      Versand nach Deutschland. Achtung zerbrechlich.\n" +
            "   </desc>\n" +
            "   start=20.01.2020 12:12\n" +
            "   end=21.01.2020 14:14\n" +
            "   category_id=1\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=Amazon Paket P45690\n" +
            "   <desc>\n" +
            "      Kostenloser Versand nach Österreich.\n" +
            "   </desc>\n" +
            "   start=22.01.2020 12:12\n" +
            "   end=23.01.2020 00:14\n" +
            "   category_id=1\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=Zalando P56783\n" +
            "   <desc>\n" +
            "      Versand nach Frankreich. Unterschrift nicht vergessen.\n" +
            "   </desc>\n" +
            "   start=24.01.2020 04:32\n" +
            "   end=25.01.2020 23:14\n" +
            "   category_id=1\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=Privates Paket P49234\n" +
            "   <desc>\n" +
            "      Private Lieferung nach Deutschland. Achtung kann Spuren von Lebensmitteln enthalten.\n" +
            "   </desc>\n" +
            "   start=24.01.2020 04:32\n" +
            "   end=25.01.2020 23:14\n" +
            "   category_id=2\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=RSa Brief B88342\n" +
            "   <desc>\n" +
            "      Behördliches Dokument. Ausweispflicht.\n" +
            "   </desc>\n" +
            "   start=29.01.2020 08:22\n" +
            "   end=29.01.2020 09:14\n" +
            "   category_id=2\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=OBI Paket P107045\n" +
            "   <desc>\n" +
            "      Lieferung nach Österreich. Achtung über 3m. Vor Nässe schützen.\n" +
            "   </desc>\n" +
            "   start=29.01.2020 08:22\n" +
            "   end=29.01.2020 09:14\n" +
            "   category_id=3\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=Rsb Brief B451113\n" +
            "   <desc>\n" +
            "      Behördliches Dokument. Streng vertraulich.\n" +
            "   </desc>\n" +
            "   start=01.01.2020 20:22\n" +
            "   end=01.01.2020 21:14\n" +
            "   category_id=4\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=Übersee Paket P344455\n" +
            "   <desc>\n" +
            "      Lieferung nach Japan. Achtung Zoll.\n" +
            "   </desc>\n" +
            "   start=30.12.2019 08:22\n" +
            "   end=31.12.2019 09:14\n" +
            "   category_id=4\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=IKEA Paket P32190\n" +
            "   <desc>\n" +
            "      Lieferung nach Deutschland. Zerbrechlich.\n" +
            "   </desc>\n" +
            "   start=29.12.2020 08:22\n" +
            "   end=29.12.2020 09:14\n" +
            "   category_id=4\n" +
            "</dataset>\n" +
            "\n" +
            "<dataset>\n" +
            "   title=BIPA Paket P000478\n" +
            "   <desc>\n" +
            "      Lieferung nach Italien. Entflammbare Stoffe.\n" +
            "   </desc>\n" +
            "   start=22.12.2020 08:22\n" +
            "   end=31.12.2020 09:14\n" +
            "   category_id=4\n" +
            "</dataset>";

    public static final String TEST_FILE_SIMPLE = "<dataset>\n" +
            "   title=Paket P1287\n" +
            "   <desc>\n" +
            "      Versand nach Deutschland. Achtung zerbrechlich.\n" +
            "   </desc>\n" +
            "   start=20.01.2020 12:12\n" +
            "   end=21.01.2020 14:14\n" +
            "   category_id=1\n" +
            "</dataset>";

}
