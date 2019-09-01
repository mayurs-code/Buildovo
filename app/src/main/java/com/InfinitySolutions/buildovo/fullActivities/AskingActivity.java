package com.InfinitySolutions.buildovo.fullActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.InfinitySolutions.buildovo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AskingActivity extends AppCompatActivity {

    String username = "UserName";
    ArrayList<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking);
        sendMessage();

    }

    public void sendMessage() {

        FloatingActionButton fab = findViewById(R.id.floatingSend);
        final ListView message_list = findViewById(R.id.message_listview);
        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,messages);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_sample_white, messages);
        messages.add("XYZ");
        messages.add("XYZ");
        messages.add("XYZ");
        messages.add("XYZ");
        message_list.setAdapter(arrayAdapter);
        TextView v = message_list.findViewById(R.id.sample_text_list);
        //System.out.println(((TextView)v.getText()).toString());
//        System.out.println(((TextView)message_list.getChildAt(0).findViewById(R.id.sample_text_list)).getText());
        // TextView tvx=(TextView)message_list.getAdapter().getView(0, null, message_list);
        //tvx.setText("Hello World");
        //message_list.removeAllViews();
        //message_list.addView(tvx);
        System.out.println();
        //itterateList(message_list);
        addMessageAi(message_list, "Hello There, Thanks for reaching out to us, we will reply you wi", 1);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText editText = findViewById(R.id.message_box);
                //if(editText.getText().toString().equals(""))
                addMessage(message_list, editText.getText().toString());
                //editText.setText("");

            }
        });
    }

    public void addMessage(final ListView message_list, String x) {

        ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) message_list.getAdapter();
        message_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        messages.add(x);
        message_list.post(new Runnable() {

            public void run() {
                message_list.setSelection(message_list.getCount());
            }
        });
    }

    int p;

    public void addMessageAi(final ListView message_list, String x, int pep) {

        ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) message_list.getAdapter();
        messages.add(x);
        arrayAdapter.notifyDataSetChanged();
        final int mposition = message_list.getCount() - 1;
        p = pep;
        message_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (p == 1) {
                    TextView tv = (TextView) view;

                    tv.setBackground(getDrawable(R.drawable.orange_yellow_gradiant));
                    tv.setTextColor(getResources().getColor(R.color.white));
                    tv.setGravity(Gravity.START);
                    message_list.setSelection(message_list.getCount() - 1);
                    System.out.println(message_list.getCount());
                }
                p = 0;


            }
        });


        final Handler h = new Handler();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(mposition);
                message_list.performItemClick(
                        message_list.getChildAt(mposition),
                        mposition,
                        message_list.getAdapter().getItemId(mposition));




            }

        };
        h.post(r);
        h.post(new Runnable() {
            @Override
            public void run() {
                //h.removeCallbacks(r);
            }
        });


    }

    public void itterateList(ListView listView) {
        TextView item;
        for (int i = 0; i <= listView.getLastVisiblePosition() - listView.getFirstVisiblePosition(); i++) {

            item = listView.getChildAt(i).findViewById(R.id.sample_text_list);
            if (item != null) {
                System.out.println(item.getText());
                item.setBackground(getDrawable(R.drawable.orange_yellow_gradiant));
            }
        }
    }
}
