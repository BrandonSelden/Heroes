package com.example.heroes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeroesListActivity extends AppCompatActivity {
    private List<Hero> heroList;
    private String jsonFileText;
    private ListView listViewHero;
    public static final String POSITION = "position";
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes_list);
        InputStream jsonInputStream = getResources().openRawResource(R.raw.heroes);
        jsonFileText = readTextFile(jsonInputStream);
        wireWidgets();
        createGSON();
        adapter = new HeroAdapter(heroList);
        listViewHero.setAdapter(adapter);
        setListeners();
    }

    private void wireWidgets() {
        listViewHero = findViewById(R.id.listView_activity_heroes_list_list);
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }//take from https://stackoverflow.com/questions/15912825/how-to-read-file-from-res-raw-by-name

    public void setListeners(){
        listViewHero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent targetIntent = new Intent(HeroesListActivity.this, HeroDetailActivity.class);
                targetIntent.putExtra(POSITION, heroList.get(i));
                startActivity(targetIntent);
                finish();
            }
        });
    }
    
    public void createGSON(){
        // create a gson object
        Gson gson = new Gson();
    // read your json file into an array of questions
        Hero[] heroes =  gson.fromJson(jsonFileText, Hero[].class);
    // convert your array to a list using the Arrays utility class
        heroList = Arrays.asList(heroes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_heroeslist_sorting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_heroeslist_sort_by_name:
                sortByName();
                return true;
            case R.id.action_heroeslist_sort_by_rank:
                sortByRank();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByRank() {
        Collections.sort(heroList, new Comparator<Hero>(){
            @Override
            public int compare(Hero hero, Hero h1){
                return hero.getRank() - h1.getRank();
            }
        });
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Sort by rank clicked", Toast.LENGTH_SHORT).show();
    }

    private void sortByName() {
        Collections.sort(heroList, new Comparator<Hero>() {
            @Override
            public int compare(Hero hero , Hero t1) {
                return hero.getName().toLowerCase()
                        .compareTo(t1.getName().toLowerCase());
            }
        });
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Sort by name clicked", Toast.LENGTH_SHORT).show();
    }

    private class HeroAdapter extends ArrayAdapter<Hero>{
        private List<Hero> heroesList;
        //since we're in the HeroListActivity class, we already have the context
        //we're hardcoding in a particular layout, so we don't need to put it in the constructor either
        public HeroAdapter(List<Hero> heroesList) {
            super(HeroesListActivity.this, -1, heroesList);
            this.heroesList = heroesList;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_hero, parent, false);
            }
            TextView textViewName = convertView.findViewById(R.id.textView_itemhero_name);
            TextView textViewRank = convertView.findViewById(R.id.textView_itemhero_rank);
            TextView textViewDescription = convertView.findViewById(R.id.textView_itemhero_description);
            textViewName.setText(heroesList.get(position).getName());
            textViewRank.setText(heroesList.get(position).getRank()+"");
            textViewDescription.setText(heroesList.get(position).getDescription());
            return convertView;
        }
    }
}
