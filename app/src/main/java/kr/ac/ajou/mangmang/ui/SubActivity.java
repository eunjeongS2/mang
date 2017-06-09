package kr.ac.ajou.mangmang.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.mangmang.Model.Item;
import kr.ac.ajou.mangmang.Model.ItemModel;
import kr.ac.ajou.mangmang.Model.OnItemFetchListener;
import kr.ac.ajou.mangmang.R;

public class SubActivity extends AppCompatActivity {
    private List<Item> itemList = new ArrayList<>();

    private RecyclerView recyclerView;

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView memoText;
        private TextView ddayText;

        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            titleText = (TextView) itemView.findViewById(R.id.title_text);
            memoText = (TextView) itemView.findViewById(R.id.memo_text);
            ddayText = (TextView) itemView.findViewById(R.id.dday_text);
        }

        View getView() {
            return view;
        }

        void setItem(Item item) {
            titleText.setText(item.getTitle());
            memoText.setText(item.getMemo());
            ddayText.setText("D-" + String.valueOf(item.getdDday()));

        }
    }


    private class SubAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.sub_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Item item = itemList.get(position);
            holder.setItem(item);

            holder.getView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final View view = LayoutInflater.from(SubActivity.this).inflate(R.layout.dialog_add, null);

                    EditText title = (EditText) view.findViewById(R.id.title_edit);
                    EditText memo = (EditText) view.findViewById(R.id.memo_edit);

                    int pos = holder.getAdapterPosition();
                    final Item item = itemList.get(pos);

                    title.setText(item.getTitle());
                    memo.setText(item.getMemo());

                    final AlertDialog alertDialog = new AlertDialog.Builder(SubActivity.this)
                            .setView(view)
                            .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    String group = ((EditText) view.findViewById(R.id.group_edit)).getText().toString();
//                                    String title = ((EditText) view.findViewById(R.id.title_edit)).getText().toString();
//                                    String memo = ((EditText) view.findViewById(R.id.memo_edit)).getText().toString();
//
//                                    int dYear = ((DatePicker) view.findViewById(R.id.date_picker)).getYear();
//                                    int dMonth = ((DatePicker) view.findViewById(R.id.date_picker)).getMonth();
//                                    int dDay = ((DatePicker) view.findViewById(R.id.date_picker)).getDayOfMonth();
//
//                                    ref.child(item.id).setValue("items");

                                }
                            })
                            .setNegativeButton("취소", null).create();


                    alertDialog.show();
//
                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        SubAdapter subAdapter = new SubAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(subAdapter);

        ItemModel itemModel = new ItemModel();
        itemModel.fetchItem(getIntent().getStringExtra("group"), new OnItemFetchListener() {
            @Override
            public void onFetch(List<Item> items) {
                itemList = items;
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }


}

