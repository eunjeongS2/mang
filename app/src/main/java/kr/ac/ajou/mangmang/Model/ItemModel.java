package kr.ac.ajou.mangmang.Model;


import android.icu.util.Calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    private List<Item> items = new ArrayList<>();

    public void fetchItem(String group, final OnItemFetchListener listener) {
        DatabaseReference ref;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("items").child(group);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items = new ArrayList<>();

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot e : children) {

                    Item item = e.getValue(Item.class);
                    item.setdDay(calDday(item));
                    items.add(item);
                }

                listener.onFetch(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int calDday(Item item) {
        int resultNumber;

        long d;
        long t;
        long r;

        Calendar currentCalendar = Calendar.getInstance();

        Calendar settingCalendar = Calendar.getInstance();
        settingCalendar.set(item.getdYear(), item.getdMonth(), item.getdDay());

        t = currentCalendar.getTimeInMillis();
        d = settingCalendar.getTimeInMillis();
        r = (d-t) / (24*60*60*1000);

        resultNumber = (int)r+1;
        return resultNumber;
    }
}
