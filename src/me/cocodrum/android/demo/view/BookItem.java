/**
 * @(#)BookItem.java, Aug 10, 2013. 
 * 
 */
package me.cocodrum.android.demo.view;

import me.cocodrum.android.demo.R;
import me.cocodrum.android.demo.data.Book;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author xuhongfeng
 *
 */
public class BookItem extends RelativeLayout {
    private TextView nameView;
    private TextView priceView;
    private TextView countView;
    private Button btnIncr;
    private Button btnDesc;

    /**
     * @param context
     */
    public BookItem(Context context) {
        super(context);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_book_item, this, true);
        
        nameView = (TextView) findViewById(R.id.text_name);
        priceView = (TextView) findViewById(R.id.text_price);
        countView = (TextView) findViewById(R.id.text_count);
        btnIncr = (Button) findViewById(R.id.btn_incr);
        btnDesc = (Button) findViewById(R.id.btn_desc);
        btnIncr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChanged(BookItem.this, true);
                }
            }
        });
        btnDesc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChanged(BookItem.this, false);
                }
            }
        });
    }
    
    public void renderCount(int count) {
        countView.setText(String.valueOf(count));
    }
    
    public void render(Book book, int count) {
        nameView.setText(book.getName());
        priceView.setText(String.format("$%.2f", book.getPrice()));
        renderCount(count);
    }
    
    private BookItemListener listener;
    public void setListener(BookItemListener l) {
        this.listener = l;
    }
    
    public interface BookItemListener {
        public void onChanged(BookItem view, boolean incr);
    }
}
