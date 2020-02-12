package at.htlgkr.parceltracker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParcelFileManager {
    final String TAG = "ParcelFileManager";

    public List<Parcel> loadParcels(InputStream fis, List<Category> categories) throws IOException {
        List<Parcel> parcelList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

        String content = reader.lines().map(n -> n.trim()).reduce((a,b) -> a+b).get();

        reader.close();



        content = content.replaceAll("\n", "");
//        Log.d(TAG, "loadParcels: content: " + content);
        String[] datasets = content.split("</dataset><dataset>");
//        Log.d(TAG, "loadParcels: " + Arrays.toString(datasets));

        int counter = 1;
        for (String s : datasets){
            String title = s.split("title=")[1].split("<desc>")[0];
//            Log.d(TAG, "loadParcels: title#" + counter + ":" + title);

            String desc = s.split("<desc>")[1].split("</desc>")[0];
//            Log.d(TAG, "loadParcels: desc#" + counter + ":" + desc);

            String start = s.split("start=")[1].split("end=")[0];
//            Log.d(TAG, "loadParcels: start#" + counter + ":" + start);

            String end = s.split("end=")[1].split("category_id=")[0];
//            Log.d(TAG, "loadParcels: end#" + counter + ":" + end);

            String categoryId = s.split("category_id=")[1].trim();
//            Log.d(TAG, "loadParcels: catID#" + counter + ":" + categoryId);
            categoryId = categoryId.split("</dataset>")[0];

            parcelList.add(new Parcel(title, desc, LocalDateTime.from(formatter.parse(start)), LocalDateTime.from(formatter.parse(end)), categories.get(Integer.valueOf(categoryId)-1)));
            counter++;
        }



        // implement this
//        return parcelList;
        return parcelList;
    }

    public void saveParcels(OutputStream fos, List<Parcel> parcels){
        // implement this

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        String contentToWrite = "";
        for(Parcel parcel : parcels){
            contentToWrite += "<dataset>\n";

            contentToWrite += "title=" + parcel.getTitle() + "\n";
            contentToWrite += "<desc>" + "\n";
            contentToWrite += parcel.getDescription() + "\n";
            contentToWrite += "</desc>" + "\n";
            contentToWrite += "start=" + formatter.format(parcel.getStart()) + "\n";
            contentToWrite += "end=" + formatter.format(parcel.getEnd()) + "\n";
            contentToWrite += "category_id=" + parcel.getCategory().getId() + "\n";

            contentToWrite += "<dataset>" + "\n\n";
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

//        contentToWrite = "<dataset>\n" +
//                "   title=Paket P1287\n" +
//                "   <desc>\n" +
//                "      Versand nach Deutschland. Achtung zerbrechlich.\n" +
//                "   </desc>\n" +
//                "   start=20.01.2020 12:12\n" +
//                "   end=21.01.2020 14:14\n" +
//                "   category_id=1\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=Amazon Paket P45690\n" +
//                "   <desc>\n" +
//                "      Kostenloser Versand nach Österreich.\n" +
//                "   </desc>\n" +
//                "   start=22.01.2020 12:12\n" +
//                "   end=23.01.2020 00:14\n" +
//                "   category_id=1\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=Zalando P56783\n" +
//                "   <desc>\n" +
//                "      Versand nach Frankreich. Unterschrift nicht vergessen.\n" +
//                "   </desc>\n" +
//                "   start=24.01.2020 04:32\n" +
//                "   end=25.01.2020 23:14\n" +
//                "   category_id=1\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=Privates Paket P49234\n" +
//                "   <desc>\n" +
//                "      Private Lieferung nach Deutschland. Achtung kann Spuren von Lebensmitteln enthalten.\n" +
//                "   </desc>\n" +
//                "   start=24.01.2020 04:32\n" +
//                "   end=25.01.2020 23:14\n" +
//                "   category_id=2\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=RSa Brief B88342\n" +
//                "   <desc>\n" +
//                "      Behördliches Dokument. Ausweispflicht.\n" +
//                "   </desc>\n" +
//                "   start=29.01.2020 08:22\n" +
//                "   end=29.01.2020 09:14\n" +
//                "   category_id=2\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=OBI Paket P107045\n" +
//                "   <desc>\n" +
//                "      Lieferung nach Österreich. Achtung über 3m. Vor Nässe schützen.\n" +
//                "   </desc>\n" +
//                "   start=29.01.2020 08:22\n" +
//                "   end=29.01.2020 09:14\n" +
//                "   category_id=3\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=Rsb Brief B451113\n" +
//                "   <desc>\n" +
//                "      Behördliches Dokument. Streng vertraulich.\n" +
//                "   </desc>\n" +
//                "   start=01.01.2020 20:22\n" +
//                "   end=01.01.2020 21:14\n" +
//                "   category_id=4\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=Übersee Paket P344455\n" +
//                "   <desc>\n" +
//                "      Lieferung nach Japan. Achtung Zoll.\n" +
//                "   </desc>\n" +
//                "   start=30.12.2019 08:22\n" +
//                "   end=31.12.2019 09:14\n" +
//                "   category_id=4\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=IKEA Paket P32190\n" +
//                "   <desc>\n" +
//                "      Lieferung nach Deutschland. Zerbrechlich.\n" +
//                "   </desc>\n" +
//                "   start=29.12.2020 08:22\n" +
//                "   end=29.12.2020 09:14\n" +
//                "   category_id=4\n" +
//                "</dataset>\n" +
//                "\n" +
//                "<dataset>\n" +
//                "   title=BIPA Paket P000478\n" +
//                "   <desc>\n" +
//                "      Lieferung nach Italien. Entflammbare Stoffe.\n" +
//                "   </desc>\n" +
//                "   start=22.12.2020 08:22\n" +
//                "   end=31.12.2020 09:14\n" +
//                "   category_id=4\n" +
//                "</dataset>\n";


        try {
            writer.write(contentToWrite);
            writer.flush();
        } catch (IOException e) {
            Log.d(TAG, "saveParcels: ERROR while saving");
        }


    }
}
