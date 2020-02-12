package at.htlgkr.parceltracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    ConfigurationParser configurationParser;
    ParcelManager parcelManager;
    ListView listView;
    ParcelFileManager parcelFileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurationParser = new ConfigurationParser();
        try {
            parcelManager = new ParcelManager(configurationParser.getCategories(getInputStreamForAsset("categories.conf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        parcelFileManager = new ParcelFileManager();
        try {
            parcelManager.setParcelList(parcelFileManager.loadParcels(openFileInput("data.db"), parcelManager.getCategories()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setupCategories();
        setupListView();
        setupButtons();

        //implement this
//        try {
//            ConfigurationParser.getCategories(getInputStreamForAsset("categories.conf"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ParcelFileManager parcelFileManager = new ParcelFileManager();
//        try {
//            parcelFileManager.loadParcels(openFileInput("data.db"), ConfigurationParser.getCategories(getInputStreamForAsset("categories.conf")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private void setupCategories(){
        Spinner spinner = findViewById(R.id.sp_categories);

        spinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                parcelManager.getCategories()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerItemSelected(adapterView, view, i, l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void spinnerItemSelected(AdapterView<?> adapterView, View view, int i, long l){
        Category cat = (Category) adapterView.getItemAtPosition(i);
        Log.d(TAG, "spinnerItemSelected: " + cat.getName());
        ParcelAdapter adapter = new ParcelAdapter(getApplicationContext(),
                R.layout.lvi_customlistviewitem,
                parcelManager.getParcelListForCategoryId(cat.getId()));

        listView.setAdapter(adapter);
    }


    private void setupListView(){
        listView = findViewById(R.id.lv_mainListView);

        ParcelAdapter adapter = new ParcelAdapter(getApplicationContext(),R.layout.lvi_customlistviewitem,parcelManager.getTicketList());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickedOnParcel(adapterView, view, i ,l);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                longClickedOnParcel(adapterView, view, i, l);
                return true;
            }
        });


    }

    private void clickedOnParcel(AdapterView<?> adapterView, View view, int i, long l){
        final View vDialog = getLayoutInflater().inflate(R.layout.dlg_addparceldialoglayout, null);

        Parcel parcel = (Parcel) adapterView.getItemAtPosition(i);

        new AlertDialog.Builder(this)
                .setMessage(parcel.getDescription())
                .setTitle(parcel.getTitle())
                .setNeutralButton("ok", null)
                .show();
    }

    private void longClickedOnParcel(AdapterView<?> adapterView, View view, int i, long l){
        Parcel parcel = (Parcel) adapterView.getItemAtPosition(i);
        parcel.setCategory(parcelManager.getNextCategory(parcel.getCategory()));

    }

    private void setupButtons(){
        Button addButton = findViewById(R.id.btn_addticket);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonClicked(view);
            }
        });


        Button setupButton = findViewById(R.id.btn_save);
        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    parcelFileManager.saveParcels(openFileOutput("data.db", MODE_PRIVATE), parcelManager.getTicketList());
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "onClick: ERROR while saving");
                }
            }
        });


        Button hourButton = findViewById(R.id.btn_calculateTotalHours);
        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourButtonClicked(view);
            }
        });
    }
    private void hourButtonClicked(View view){
        Spinner selCat = findViewById(R.id.sp_categories);
        Category seleCat = (Category) selCat.getSelectedItem();
        new AlertDialog.Builder(this)
                .setMessage("Current category: " + parcelManager.calcHoursByCategory(seleCat) + "\nTotal hours: " + parcelManager.calcTotalHours())
                .setNeutralButton("ok", null)
                .show();
    }

    private void addButtonClicked(View view){
        final View vDialog = getLayoutInflater().inflate(R.layout.dlg_addparceldialoglayout, null);
        new AlertDialog.Builder(this)
                .setMessage("Add new Parcel")
                .setCancelable(false)
                .setView(vDialog)
//                .setPositiveButton("Add", (dialog,which) -> null)
                .setPositiveButton("Add", ( dialog, which) -> handleButtonClickedAddInDialog(dialog,vDialog))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void handleButtonClickedAddInDialog(DialogInterface dialogInterface, View dialog){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        EditText txtTitle = dialog.findViewById(R.id.et_title);
        EditText txtDesc = dialog.findViewById(R.id.et_description);
        EditText txtStart = dialog.findViewById(R.id.et_start);
        LocalDateTime start = null;
        EditText txtEnd = dialog.findViewById(R.id.et_end);
        LocalDateTime end = null;

        if(txtTitle.getText().toString().equals("")){
            Toast.makeText(this, Configuration.YOU_HAVE_TO_ENTER_A_TITLE, Toast.LENGTH_SHORT).show();
        }

        else{
            try{
                start = LocalDateTime.from(formatter.parse(txtStart.getText().toString()));
            }
            catch(Exception e){
                Toast.makeText(this, Configuration.THE_GIVEN_START_DATE_TIME_COULD_NOT_BE_PARSED, Toast.LENGTH_SHORT).show();
            }

            try{
                end = LocalDateTime.from(formatter.parse(txtEnd.getText().toString()));
            }
            catch(Exception e){
                Toast.makeText(this, Configuration.THE_GIVEN_END_DATE_TIME_COULD_NOT_BE_PARSED, Toast.LENGTH_SHORT).show();
            }



            parcelManager.addTicket(new Parcel(txtTitle.getText().toString(),
                    txtDesc.getText().toString(),
                    start,
                    end,
                    parcelManager.getStandardCategory()));

            updateListView();
        }

    }




    private void updateListView(){
        listView = findViewById(R.id.lv_mainListView);

        ParcelAdapter adapter = new ParcelAdapter(getApplicationContext(),R.layout.lvi_customlistviewitem,parcelManager.getTicketList());
        listView.setAdapter(adapter);
    }



    private InputStream getInputStreamForAsset(String filename) {
// tries to open Stream on Assets. If fails, returns null
        Log.d(TAG, "getInputStreamForAsset: " + filename);
        AssetManager assets = getAssets();
        try {
            return assets.open(filename);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            return null;
        }
    }
}