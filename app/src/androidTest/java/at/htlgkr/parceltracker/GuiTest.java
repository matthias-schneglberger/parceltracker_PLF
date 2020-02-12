package at.htlgkr.parceltracker;

import android.widget.ListView;
import android.widget.Spinner;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GuiTest {
    @Rule
    public ActivityTestRule<MainActivity> myActivityRule = new ActivityTestRule<>(MainActivity.class);

    @After
    public void cleanup() {
        resetFile();
    }

    @Test
    public void gui_categories_loadsSomething() {
        Spinner underTest = myActivityRule.getActivity().findViewById(R.id.sp_categories);

        List<Category> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Category);

            actual.add((Category) underTest.getAdapter().getItem(i));
        }

        assertTrue(actual.size() > 0);
    }

    @Test
    public void gui_categories_correctLength() {
        Spinner underTest = myActivityRule.getActivity().findViewById(R.id.sp_categories);

        List<Category> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Category);

            actual.add((Category) underTest.getAdapter().getItem(i));
        }

        assertEquals(4, actual.size());
    }

    @Test
    public void gui_categories_correctData() {
        Spinner underTest = myActivityRule.getActivity().findViewById(R.id.sp_categories);

        List<Category> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Category);

            actual.add((Category) underTest.getAdapter().getItem(i));
        }

        ListTester lt = new ListTester();
        assertTrue(lt.listsEqual(actual, getAllCategories()));
    }

    @Test
    public void gui_categories_correctSelectedAtStartup() {
        Spinner underTest = myActivityRule.getActivity().findViewById(R.id.sp_categories);

        assertTrue(underTest.getSelectedItem() instanceof Category);

        ListTester<Category> lt = new ListTester<>();

        assertTrue(lt.categoryEquals(getCategoryById(1), (Category) underTest.getSelectedItem()));
    }

    @Test
    public void gui_listView_startup_loadsSomething() {
        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        assertTrue(actual.size() > 0);
    }

    @Test
    public void gui_listView_startup_correctSize() {

        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        assertEquals(3, actual.size());
    }

    @Test
    public void gui_listView_startup_correctData() {

        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        List<Parcel> expected = getParcelsByCategoryId(1);

        ListTester<Parcel> lt = new ListTester<>();

        assertTrue(lt.listsEqual(actual, expected));
    }

    @Test
    public void gui_listView_categorychange_doesSomething() {

        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);

        List<Parcel> before = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            before.add((Parcel) underTest.getAdapter().getItem(i));
        }

        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("In Transit")).perform(click());

        List<Parcel> after = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            after.add((Parcel) underTest.getAdapter().getItem(i));
        }

        ListTester<Parcel> lt = new ListTester<>();

        assertFalse(lt.listsEqual(before, after));
    }

    @Test
    public void gui_listView_categorychange_correctSize() {

        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("In Transit")).perform(click());

        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        assertEquals(2, actual.size());
    }

    @Test
    public void gui_listView_categorychange_correctData() {

        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("In Transit")).perform(click());


        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        List<Parcel> expected = getParcelsByCategoryId(2);

        ListTester<Parcel> lt = new ListTester<>();

        assertTrue(lt.listsEqual(actual, expected));
    }

    @Test
    public void gui_listView_categorychange_correctData_all() {

        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("In Transit")).perform(click());


        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        List<Parcel> expected = getParcelsByCategoryId(2);

        ListTester<Parcel> lt = new ListTester<>();

        assertTrue(lt.listsEqual(actual, expected));


        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("Delivered")).perform(click());

        actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        expected = getParcelsByCategoryId(3);


        assertTrue(lt.listsEqual(actual, expected));


        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("Expired")).perform(click());

        actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        expected = getParcelsByCategoryId(4);


        assertTrue(lt.listsEqual(actual, expected));
    }

    @Test
    public void gui_addDataDialog_dialogOpens() {
        onView(withId(R.id.btn_addticket)).perform(click());
        onView(withText("Description")).check(matches(isDisplayed()));
    }

    @Test
    public void gui_addDataDialog_canCancel() {
        onView(withId(R.id.btn_addticket)).perform(click());
        onView(withId(android.R.id.button2)).perform(click());
    }

    @Test
    public void gui_addDataDialog_addParcel() {
        onView(withId(R.id.btn_addticket)).perform(click());

        onView(withId(R.id.et_title)).perform(typeText("Test Parcel"));
        onView(withId(R.id.et_description)).perform(typeText("Dies ist eine extensive description des Pakets und wird im Desc-Tag abgespeichert."));
        onView(withId(R.id.et_start)).perform(typeText("12.12.2020 12:30"));
        onView(withId(R.id.et_end)).perform(typeText("12.12.2020 15:30"));

        onView(withId(android.R.id.button1)).perform(click());


        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        List<Parcel> expected = getParcelsByCategoryId(1);

        expected.add(getTestParcel());

        ListTester lt = new ListTester();

        assertTrue(lt.listsEqual(actual, expected));
    }

    @Test
    public void gui_addDataDialog_showsTitleErrorToast() {
        onView(withId(R.id.btn_addticket)).perform(click());

        onView(withId(android.R.id.button1)).perform(click());

        onView(withText(Configuration.YOU_HAVE_TO_ENTER_A_TITLE)).
                inRoot(withDecorView(not(is(myActivityRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void gui_addDataDialog_showsStartErrorToast() {
        onView(withId(R.id.btn_addticket)).perform(click());

        onView(withId(R.id.et_title)).perform(typeText("Test Parcel"));
        onView(withId(R.id.et_start)).perform(typeText("asdf"));
        onView(withId(R.id.et_end)).perform(typeText("12.12.2020 15:30"));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withText(Configuration.THE_GIVEN_START_DATE_TIME_COULD_NOT_BE_PARSED)).
                inRoot(withDecorView(not(is(myActivityRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void gui_addDataDialog_showsEndErrorToast() {
        onView(withId(R.id.btn_addticket)).perform(click());

        onView(withId(R.id.et_title)).perform(typeText("Test Parcel"));
        onView(withId(R.id.et_start)).perform(typeText("12.12.2020 15:30"));
        onView(withId(R.id.et_end)).perform(typeText("asdf"));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withText(Configuration.THE_GIVEN_END_DATE_TIME_COULD_NOT_BE_PARSED)).
                inRoot(withDecorView(not(is(myActivityRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void gui_shiftData_oneToAnother_removed() {
        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);
        assertTrue(underTest.getAdapter().getItem(0) instanceof Parcel);
        Parcel shiftedParcel = (Parcel) underTest.getAdapter().getItem(0);

        List<Parcel> ticketListWithRemovedShiftParcel = removeParcelFromList(getParcelsByCategoryId(1), shiftedParcel);

        onData(anything()).inAdapterView(withId(R.id.lv_mainListView)).atPosition(0).perform(longClick());

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }


        ListTester<Parcel> lt = new ListTester<>();

        assertTrue(lt.listsEqual(ticketListWithRemovedShiftParcel, actual));
    }

    @Test
    public void gui_shiftData_oneToAnother_addedToTheOtherList() {
        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);
        assertTrue(underTest.getAdapter().getItem(0) instanceof Parcel);
        Parcel shiftedParcel = (Parcel) underTest.getAdapter().getItem(0);

        onData(anything()).inAdapterView(withId(R.id.lv_mainListView)).atPosition(0).perform(longClick());

        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("In Transit")).perform(click());

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        shiftedParcel.setCategory(new Category(2, "In Transit"));


        ListTester<Parcel> lt = new ListTester<>();

        List<Parcel> expected = getParcelsByCategoryId(2);
        expected.add(shiftedParcel);

        assertTrue(lt.listsEqual(expected, actual));
    }

    @Test
    public void gui_shiftData_lastCategoryFunctionality() {
        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);
        assertTrue(underTest.getAdapter().getItem(0) instanceof Parcel);

        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("Expired")).perform(click());

        onData(anything()).inAdapterView(withId(R.id.lv_mainListView)).atPosition(0).perform(longClick());

        List<Parcel> actual = new ArrayList<>();

        for (int i = 0; i < underTest.getAdapter().getCount(); i++) {
            assertTrue(underTest.getAdapter().getItem(i) instanceof Parcel);

            actual.add((Parcel) underTest.getAdapter().getItem(i));
        }

        ListTester<Parcel> lt = new ListTester<>();

        List<Parcel> expected = getParcelsByCategoryId(4);

        assertTrue(lt.listsEqual(expected, actual));
    }

    @Test
    public void gui_dataDetails_dialogCorrect() {
        ListView underTest = myActivityRule.getActivity().findViewById(R.id.lv_mainListView);
        assertTrue(underTest.getAdapter().getItem(0) instanceof Parcel);
        Parcel detailParcel = (Parcel) underTest.getAdapter().getItem(0);

        onData(anything()).inAdapterView(withId(R.id.lv_mainListView)).atPosition(0).perform(click());

        onView(withText(detailParcel.getDescription())).check(matches(isDisplayed()));
        onView(withText(detailParcel.getTitle())).check(matches(isDisplayed()));
    }

    @Test
    public void gui_calculate_hours_backlog() {
        onView(withId(R.id.btn_calculateTotalHours)).perform(click());
        onView(withText(String.format("Current category: %d\nTotal hours: %d", 80, 362))).check(matches(isDisplayed()));
    }

    @Test
    public void gui_calculate_hours_next() {
        onView(withId(R.id.sp_categories)).perform(click());
        onView(withText("In Transit")).perform(click());
        onView(withId(R.id.btn_calculateTotalHours)).perform(click());
        onView(withText(String.format("Current category: %d\nTotal hours: %d", 42, 362))).check(matches(isDisplayed()));
    }

    @Test
    public void gui_save_doesSomething() {
        String oldc = readDataFile();

        onView(withId(R.id.btn_addticket)).perform(click());

        onView(withId(R.id.et_title)).perform(typeText("Test Parcel"));
        onView(withId(R.id.et_description)).perform(typeText("Dies ist eine extensive description des Pakets und wird im Desc-Tag abgespeichert."));
        onView(withId(R.id.et_start)).perform(typeText("12.12.2020 12:30"));
        onView(withId(R.id.et_end)).perform(typeText("12.12.2020 15:30"));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.btn_save)).perform(click());

        String newc = readDataFile();

        assertNotEquals(oldc, newc);
    }

    @Test
    public void gui_save_stores() {
        onView(withId(R.id.btn_addticket)).perform(click());

        onView(withId(R.id.et_title)).perform(typeText("Test Parcel"));
        onView(withId(R.id.et_description)).perform(typeText("Test desc"));
        onView(withId(R.id.et_start)).perform(typeText("12.12.2020 12:30"));
        onView(withId(R.id.et_end)).perform(typeText("12.12.2020 15:30"));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.btn_save)).perform(click());
        onView(withId(R.id.btn_save)).perform(click());

        String newc = readDataFile();

        assertTrue(newc.contains("Test Parcel"));
        assertTrue(newc.contains("Test desc"));
        assertTrue(newc.contains("12.12.2020 12:30"));
        assertTrue(newc.contains("12.12.2020 15:30"));
    }


    private List<Parcel> removeParcelFromList(List<Parcel> lst, Parcel parcelToRemove) {
        List<Parcel> result = new ArrayList<>();

        ListTester<Parcel> lt = new ListTester<>();

        for (Parcel t : lst) {
            if (!lt.parcelEquals(t, parcelToRemove)) {
                result.add(t);
            }
        }

        return result;
    }

    public Parcel getTestParcel() {
        Category c0 = new Category(1, "Shipping Notice Received");
        LocalDateTime s0 = LocalDateTime.parse("2020-12-12T12:30");
        LocalDateTime e0 = LocalDateTime.parse("2020-12-12T15:30");
        Parcel t0 = new Parcel("Test Parcel", "Dies ist eine extensive description des Pakets und wird im Desc-Tag abgespeichert.", s0, e0, c0);

        return t0;
    }

    private class ListTester<T> {
        private boolean listsEqual(List<T> listA, List<T> listB) {
            for (T cA : listA) {
                if (!isElementInList(cA, listB))
                    return false;
            }

            for (T cB : listB) {
                if (!isElementInList(cB, listA))
                    return false;
            }

            return true;
        }

        private boolean isElementInList(T needle, List<T> list) {
            for (T cElement : list) {
                if (cElement instanceof Category) {
                    if (categoryEquals((Category) cElement, (Category) needle))
                        return true;
                }
                if (cElement instanceof Parcel) {
                    if (parcelEquals((Parcel) cElement, (Parcel) needle)) {
                        return true;
                    }
                }
            }

            return false;
        }

        private boolean categoryEquals(Category a, Category b) {
            return a.getId() == b.getId() && a.getName().equals(b.getName());
        }

        private boolean parcelEquals(Parcel a, Parcel b) {
            return a.getTitle().equals(b.getTitle()) &&
                    a.getDescription().equals(b.getDescription()) &&
                    categoryEquals(a.getCategory(), b.getCategory()) &&
                    a.getStart().toString().equals(b.getStart().toString()) &&
                    a.getEnd().toString().equals(b.getEnd().toString());
        }
    }

    private Category getCategoryById(int i) {
        for (Category cCategory : getAllCategories()) {
            if (cCategory.getId() == i)
                return cCategory;
        }

        return null;
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

    public List<Parcel> getParcelsByCategoryId(int id) {
        List<Parcel> result = new ArrayList<>();

        for (Parcel cParcel : getAllParcels()) {
            if (cParcel.getCategory().getId() == id)
                result.add(cParcel);
        }

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
        Parcel t5 = new Parcel("OBI Paket P107045", "Lieferund nach Österreich. Achtung über 3m. Vor Nässe schützen.", s5, e5, c5);
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

    private File deleteFileIfExists() {
        File file = new File(myActivityRule.getActivity().getFilesDir(), Configuration.DATA_FILE);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    public String readDataFile() {
        File file = new File(myActivityRule.getActivity().getFilesDir(), Configuration.DATA_FILE);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);

            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            return new String(data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void resetFile() {
        deleteFileIfExists();
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(myActivityRule.getActivity().getFilesDir(), Configuration.DATA_FILE));
            fw.write(dbFileContent);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static final String dbFileContent = "<dataset>\n" +
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
            "      Lieferund nach Österreich. Achtung über 3m. Vor Nässe schützen.\n" +
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
}