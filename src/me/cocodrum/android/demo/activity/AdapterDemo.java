/**
 * @(#)AdapterDemo.java, Aug 10, 2013. 
 * 
 */
package me.cocodrum.android.demo.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.cocodrum.android.demo.R;
import me.cocodrum.android.demo.data.Book;
import me.cocodrum.android.demo.view.BookItem;
import me.cocodrum.android.demo.view.BookItem.BookItemListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author xuhongfeng
 *
 */
public class AdapterDemo extends Activity {
    private static final String[] NAMES = {"Modern Computer OS", "Effective JAVA",
        "Thinking in Java", "Algorithms", "JavaScript Cook Book"};
    
    private Book[] books;
    private Map<Integer, Integer> bookCountMap = new HashMap<Integer, Integer>();
    
    private TextView totalPriceView;
    private ListView  listView;
    private BaseAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_demo);
        getActionBar().setTitle("BookStore");
        
        initData();
        
        listView = (ListView) findViewById(R.id.list);
        totalPriceView = (TextView) findViewById(R.id.text_total);
        render();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adapter_demo, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cancel) {
            bookCountMap.clear();
            adapter.notifyDataSetChanged();
            renderTotal();
            return true;
        }
        if (item.getItemId() == R.id.menu_submit) {
            Toast.makeText(this, "Total Price :" + getTotalPrice(),
                    Toast.LENGTH_LONG).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void render() {
        adapter = new InnerAdapter();
        listView.setAdapter(adapter);
        renderTotal();
    }
    
    private double getTotalPrice() {
        double total = 0;
        for (int bookId:bookCountMap.keySet()) {
            int count = bookCountMap.get(bookId);
            if (count > 0) {
                Book book = getBook(bookId);
                total += book.getPrice() * count;
            }
        }
        return total;
    }
    
    private void renderTotal() {
        double total = getTotalPrice();
        totalPriceView.setText(String.format("you should pay me $%.2f", total));
    }
    
    private Book getBook(int bookId) {
        if (books != null) {
            for (Book book:books) {
                if (book.getId() == bookId) {
                    return book;
                }
            }
        }
        return null;
    }
    
    private void setBookCount(int bookId, int count) {
        bookCountMap.put(bookId, count);
    }
    
    private int getBookCount(int bookId) {
        if (!bookCountMap.containsKey(bookId)) {
            bookCountMap.put(bookId, 0);
        }
        return bookCountMap.get(bookId);
    }

    private void initData() {
        Random r = new Random();
        books = new Book[NAMES.length];
        for (int i=0; i<books.length; i++) {
            books[i] = new Book();
            books[i].setName(NAMES[i]);
            books[i].setId(i+1);
            books[i].setPrice(r.nextDouble()*100);
        }
    }
    
    private class InnerAdapter extends BaseAdapter implements BookItemListener {
        
        @Override
        public void onChanged(BookItem view, boolean incr) {
            ViewHolder holder = (ViewHolder) view.getTag();
            Book book = holder.getBook();
            int count = getBookCount(book.getId());
            if (incr) {
                count++;
                setBookCount(book.getId(), count);
            } else {
                if (count > 0) {
                    count--;
                    setBookCount(book.getId(), count);
                }
            }
            view.renderCount(count);
            renderTotal();
        }

        @Override
        public int getCount() {
            return books.length;
        }

        @Override
        public Book getItem(int position) {
            return books[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public BookItem getView(int position, View convertView, ViewGroup parent) {
            BookItem view = (BookItem) convertView;
            if (view == null) {
                view = createView();
            }
            Book book = getItem(position);
            view.render(book, getBookCount(book.getId()));
            bindView(view, position);
            return view;
        }
        
        private BookItem createView() {
            BookItem view = new BookItem(AdapterDemo.this);
            view.setListener(this);
            return view;
        }
        
        private void bindView(BookItem view, int position) {
            Book book = getItem(position);
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                view.setTag(holder);
            }
            holder.setBook(book);
        }
    }
    
    private class ViewHolder {
        private Book book;

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }
        
    }
}
