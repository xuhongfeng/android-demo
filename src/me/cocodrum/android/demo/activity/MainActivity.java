package me.cocodrum.android.demo.activity;

import me.cocodrum.android.demo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String[] labels = new String[] {
        "Adapter"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new InnerAdapter());
    }
    
    private void onItemClick(int position) {
        if (position == 0) {
            Intent intent = new Intent(this, AdapterDemo.class);
            startActivity(intent);
        }
    }
    
    private class InnerAdapter extends BaseAdapter implements OnClickListener {
        
        @Override
        public void onClick(View v) {
            Holder holder = (Holder) v.getTag();
            MainActivity.this.onItemClick(holder.getPosition());
        }

        @Override
        public int getCount() {
            return labels.length;
        }

        @Override
        public String getItem(int position) {
            return labels[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) convertView;
            if (view == null) {
                view = createView();
            }
            bindView(view, position);
            return view;
        }
        
        private void bindView(TextView view, int position) {
            view.setText(getItem(position));
            Holder holder = (Holder) view.getTag();
            holder.setPosition(position);
        }
        
        private TextView createView() {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            TextView view = (TextView) inflater.inflate(R.layout.view_text_list_item, null);
            view.setTag(new Holder());
            view.setOnClickListener(this);
            view.setTextAppearance(MainActivity.this, R.style.ListItem_Text);
            return view;
        }
    }
    
    private class Holder {
        private int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
