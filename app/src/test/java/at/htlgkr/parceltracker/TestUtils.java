package at.htlgkr.parceltracker;

import java.util.List;

public class TestUtils {
    public static class ListTester<T> {
        public boolean listsEqual(List<T> listA, List<T> listB) {
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
                    if (ticketEquals((Parcel) cElement, (Parcel) needle)) {
                        return true;
                    }
                }
            }

            return false;
        }

        public boolean categoryEquals(Category a, Category b) {
            return a.getId() == b.getId() && a.getName().equals(b.getName());
        }

        public boolean ticketEquals(Parcel a, Parcel b) {
            return a.getTitle().equals(b.getTitle()) &&
                    a.getDescription().equals(b.getDescription()) &&
                    categoryEquals(a.getCategory(), b.getCategory()) &&
                    a.getStart().toString().equals(b.getStart().toString()) &&
                    a.getEnd().toString().equals(b.getEnd().toString());
        }
    }
}
