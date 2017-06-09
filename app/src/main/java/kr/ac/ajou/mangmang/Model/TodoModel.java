package kr.ac.ajou.mangmang.Model;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TodoModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private OnItemChangeListener listener;

    public TodoModel() {
        DatabaseReference itemRef = database.getReference("items");
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groupList = new ArrayList<>();

                Iterable<DataSnapshot> groups = dataSnapshot.getChildren();
                for (DataSnapshot e : groups) {

                    groupList.add(new Group(e.getKey(), e.getChildrenCount()));
                }

                if (listener != null) {
                    listener.onChange(groupList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<Group> groupList = new ArrayList<>();

    public void setOnItemChangedListener(final OnItemChangeListener listener) {
        this.listener = listener;
    }

    public void saveItem(String groupName, String title, String memo, int dYear, int dMonth, int dDay) {
        DatabaseReference itemRef = database.getReference("items");
        DatabaseReference childRef = itemRef.child(groupName).push();
        childRef.setValue(new Item(childRef.getKey(), groupName, title, memo, dYear, dMonth, dDay, 0));
    }
}









