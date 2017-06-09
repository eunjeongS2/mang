package kr.ac.ajou.mangmang.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.mangmang.Model.Group;
import kr.ac.ajou.mangmang.Model.OnItemChangeListener;
import kr.ac.ajou.mangmang.Model.TodoModel;
import kr.ac.ajou.mangmang.R;

public class GroupActivity extends AppCompatActivity {
    private TodoModel model = new TodoModel();

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private String groupName;
    private List<Group> groupList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView groupTextView;
        private TextView itemCountTextView;
        private View view;

        private View getView() {
            return view;
        }


        private ViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;
            groupTextView = (TextView) itemView.findViewById(R.id.group_text);
            itemCountTextView = (TextView) itemView.findViewById(R.id.item_count_text);
        }

        private void setGroupName(String name) {
            groupTextView.setText(name);
        }

        public String getGroupName() {
            return groupTextView.getText().toString();
        }

        public void setItemCount(long count) {
            itemCountTextView.setText("" + count);
        }

    }

    private class TodoAdapter extends RecyclerView.Adapter<GroupActivity.ViewHolder> {

        @Override
        public GroupActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.group_view_item, parent, false);

            return new GroupActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final GroupActivity.ViewHolder holder, int position) {
            holder.setGroupName(groupList.get(position).getName());
            holder.setItemCount(groupList.get(position).getItemCount());
            holder.getView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(GroupActivity.this, SubActivity.class);
                    intent.putExtra("group", holder.getGroupName());
                    startActivity(intent);

                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return groupList.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new GroupActivity.TodoAdapter());

        addButton = (FloatingActionButton) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = LayoutInflater.from(GroupActivity.this).inflate(R.layout.dialog_add, null);
                Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                ArrayAdapter adapter = ArrayAdapter.createFromResource(GroupActivity.this, R.array.todo_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        groupName = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                final AlertDialog alertDialog = new AlertDialog.Builder(GroupActivity.this)
                        .setView(view)
                        .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = ((EditText) view.findViewById(R.id.title_edit)).getText().toString();
                                String memo = ((EditText) view.findViewById(R.id.memo_edit)).getText().toString();

                                int dYear = ((DatePicker) view.findViewById(R.id.date_picker)).getYear();
                                int dMonth = ((DatePicker) view.findViewById(R.id.date_picker)).getMonth();
                                int dDay = ((DatePicker) view.findViewById(R.id.date_picker)).getDayOfMonth();

                                model.saveItem(groupName, title, memo, dYear, dMonth, dDay);
                            }
                        })
                        .setNegativeButton("취소", null).create();


                alertDialog.show();
            }
        });


        model.setOnItemChangedListener(new OnItemChangeListener() {
            @Override
            public void onChange(List<Group> groups) {
                groupList = groups;
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }
}
