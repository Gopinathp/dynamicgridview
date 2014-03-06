package co.uk.pa.dynamicgridview.test;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import co.uk.pa.dynamicgridview.R;
import co.uk.pa.dynamicgridview.view.DynamicGridView;

public class TestActivity extends ActionBarActivity {

    public static class Item {
        public float ratio;
        public String label;
        public int color;
    }


    private static List<List<Item>> generateItems() {
        List<List<Item>> itemsList = new ArrayList<List<Item>>();
        for (int i = 0; i < 4; i++) {
            List<Item> items = new ArrayList<Item>();
            generateColumn(items, i);
            itemsList.add(items);
        }
        return itemsList;
    }

    private static void addMoreItems(List<List<Item>> itemslist) {

        for (int i = 0; i < 4; i++) {
            List<Item> items = itemslist.get(i);
            generateColumn(items, i);
            itemslist.add(items);
        }

    }

    private static void generateColumn(List<Item> items, int col) {
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            int type = r.nextInt(3);
            switch (type) {
                case 0:
                    item.ratio = .8f;
                    break;
                case 1:
                    item.ratio = .5f;
                    break;
                case 2:
                default:
                    item.ratio = .9f;
                    break;
            }
            int red = (i * (col + 1) * 10) % 255;
            int green = (i * (col + 1) * 3) % 255;
            int blue = (i * (col + 1) * 4) % 255;
            item.color= Color.argb(150,red,green,blue);
            int number = r.nextInt(1000);
            item.label = "" + number;
            items.add(item);
        }
    }

    static List<List<Item>> sItems = generateItems();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dynamicgrid);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_test, container, false);
            DynamicGridView gridView = (DynamicGridView) rootView.findViewById(R.id.gridView);

            final TestAdapter adapter = new TestAdapter(sItems);
            gridView.setAdapter(adapter);
            rootView.findViewById(R.id.addMoreButtom).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addMoreItems(sItems);
                    adapter.notifyDatasetChanged();
                }
            });
            return rootView;
        }
    }

}
