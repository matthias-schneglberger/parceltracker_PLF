package at.htlgkr.parceltracker;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ParcelManagerTest {
    @Test
    public void parcelManager_constructor() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(underTest.getCategories(), getAllCategories()));
    }

    @Test
    public void parcelManager_addTicket() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        underTest.addTicket(getAllParcels().get(0));

        TestUtils.ListTester<Parcel> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(underTest.getTicketList(), getAllParcels().subList(0, 1)));
    }

    @Test
    public void parcelManager_setTicketList() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        underTest.setParcelList(getAllParcels());

        TestUtils.ListTester<Parcel> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(underTest.getTicketList(), getAllParcels()));
    }

    @Test
    public void parcelManager_getStandardCategory() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        TestUtils.ListTester<Parcel> lt = new TestUtils.ListTester<>();

        assertTrue(lt.categoryEquals(underTest.getStandardCategory(), getAllCategories().get(0)));
    }

    @Test
    public void parcelManager_getCategories() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(underTest.getCategories(), getAllCategories()));
    }

    @Test
    public void parcelManger_getCategoryById() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        Category is = underTest.getCategoryById(1);
        Category exp = getAllCategories().get(0);



        assertTrue(lt.categoryEquals(underTest.getCategoryById(1), getAllCategories().get(0)));
        assertTrue(lt.categoryEquals(underTest.getCategoryById(2), getAllCategories().get(1)));
        assertTrue(lt.categoryEquals(underTest.getCategoryById(3), getAllCategories().get(2)));
        assertTrue(lt.categoryEquals(underTest.getCategoryById(4), getAllCategories().get(3)));
    }

    @Test
    public void parcelManger_getTicketListForCategoryId() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        TestUtils.ListTester<Parcel> lt = new TestUtils.ListTester<>();
        underTest.setParcelList(getAllParcels());

        assertTrue(lt.listsEqual(underTest.getParcelListForCategoryId(1), getParcelsByCategoryId(1)));
        assertTrue(lt.listsEqual(underTest.getParcelListForCategoryId(2), getParcelsByCategoryId(2)));
        assertTrue(lt.listsEqual(underTest.getParcelListForCategoryId(3), getParcelsByCategoryId(3)));
        assertTrue(lt.listsEqual(underTest.getParcelListForCategoryId(4), getParcelsByCategoryId(4)));

    }

    @Test
    public void parcelManager_getNextCategory_simple() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        Category next = underTest.getNextCategory(getAllCategories().get(0));

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();
        Category exp = getAllCategories().get(1);
        assertTrue(lt.categoryEquals(next, getAllCategories().get(1)));
    }

    @Test
    public void parcelManager_getNextCategory_end() {
        ParcelManager underTest = new ParcelManager(getAllCategories());

        Category next = underTest.getNextCategory(getAllCategories().get(3));

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        assertTrue(lt.categoryEquals(next, getAllCategories().get(3)));
    }

    @Test
    public void parcelManger_calcTotalHours_calculatesSomething() {
        ParcelManager underTest = new ParcelManager(getAllCategories());
        underTest.setParcelList(getAllParcelsHourTest());

        assertTrue(underTest.calcTotalHours() > 0);
    }

    @Test
    public void parcelManger_calcTotalHours_correct() {
        ParcelManager underTest = new ParcelManager(getAllCategories());
        underTest.setParcelList(getAllParcelsHourTest());

        assertEquals(4, underTest.calcTotalHours());
    }

    @Test
    public void parcelManger_calcHoursByCategory_calculatesSomething() {
        ParcelManager underTest = new ParcelManager(getAllCategories());
        underTest.setParcelList(getAllParcelsHourTest());

        assertTrue(underTest.calcHoursByCategory(new Category(1, "Shipping Notice Received")) > 0);
    }

    @Test
    public void parcelManger_calcHoursByCategory_correct() {
        ParcelManager underTest = new ParcelManager(getAllCategories());
        underTest.setParcelList(getAllParcelsHourTest());

        assertEquals(2, underTest.calcHoursByCategory(new Category(1, "Shipping Notice Received")));
    }


    public List<Parcel> getParcelsByCategoryId(int id) {
        List<Parcel> result = new ArrayList<>();

        for(Parcel cParcel : getAllParcels()) {
            if(cParcel.getCategory().getId() == id)
                result.add(cParcel);
        }

        return result;
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

    public List<Parcel> getAllParcelsHourTest() {
        Category c1 = new Category(1, "Shipping Notice Received");
        LocalDateTime s1 = LocalDateTime.parse("2020-01-20T12:12");
        LocalDateTime e1 = LocalDateTime.parse("2020-01-20T13:12");
        Parcel t1 = new Parcel("Amazon Paket P45690", "Versand nach Deutschland. Achtung zerbrechlich.", s1, e1, c1);
        Category c2 = new Category(1, "Shipping Notice Received");
        LocalDateTime s2 = LocalDateTime.parse("2020-01-20T12:12");
        LocalDateTime e2 = LocalDateTime.parse("2020-01-20T13:12");
        Parcel t2 = new Parcel("Zalando P56783", "Kostenloser Versand nach Österreich.", s2, e2, c2);
        Category c3 = new Category(2, "In Transit");
        LocalDateTime s3 = LocalDateTime.parse("2020-01-20T12:12");
        LocalDateTime e3 = LocalDateTime.parse("2020-01-20T13:12");
        Parcel t3 = new Parcel("Privates Paket P49234", "Versand nach Frankreich. Unterschrift nicht vergessen.", s3, e3, c3);
        Category c4 = new Category(2, "In Transit");
        LocalDateTime s4 = LocalDateTime.parse("2020-01-20T12:12");
        LocalDateTime e4 = LocalDateTime.parse("2020-01-20T13:12");
        Parcel t4 = new Parcel("RSa Brief B88342", "Private Lieferung nach Deutschland. Achtung kann Spuren von Lebensmitteln enthalten.", s4, e4, c4);

        List<Parcel> result = new ArrayList<>();

        result.add(t1);
        result.add(t2);
        result.add(t3);
        result.add(t4);

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
}
