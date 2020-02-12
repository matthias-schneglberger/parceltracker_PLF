package at.htlgkr.parceltracker;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParcelManager {
    List<Category> categories;
    List<Parcel> parcelList = new ArrayList<>();


    public ParcelManager(List<Category> categories) {
        this.categories = categories;
    }

    public void addTicket(Parcel newParcel) {
        parcelList.add(newParcel);
    }

    public List<Parcel> getTicketList() {
    	// implement this
        return parcelList;
    }

    public Category getStandardCategory() {
    	// implement this
        return categories.get(0);
    }

    public List<Category> getCategories() {
    	// implement this
        return categories;
    }

    public void setParcelList(List<Parcel> parcels) {
        parcelList = parcels;
    }

    public List<Parcel> getParcelListForCategoryId(int categoryId) {
        // implement this
        return parcelList.stream().filter(n -> n.getCategory().getId() == categoryId).collect(Collectors.toList());
    }

    public Category getNextCategory(Category selectedCategory) {
//        try{
//            return categories.get(selectedCategory.getId());
//        }
//        catch (Exception e){
//            return selectedCategory;
//        }
        if(categories.size() > selectedCategory.getId()){
            return categories.get(selectedCategory.getId());
        }
        else{
            return selectedCategory;
        }
    }

    public Category getCategoryById(int categoryId) {
        // implement this
        return categories.get(categoryId-1);
    }

    public long calcHoursByCategory(Category category) {
        // implement this
        List<Parcel> tmpParcels = parcelList.stream().filter(n -> n.getCategory().getId() == category.getId()).collect(Collectors.toList());
        long time = 0;
        for(Parcel p : tmpParcels){
            time += ChronoUnit.HOURS.between(p.getStart(), p.getEnd());
        }

        return time;
    }

    public long calcTotalHours() {
        long time = 0;
        for(Parcel p : parcelList){
            time += ChronoUnit.HOURS.between(p.getStart(), p.getEnd());
        }

        return time;

    }

}
