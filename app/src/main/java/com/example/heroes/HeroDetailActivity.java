package com.example.heroes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HeroDetailActivity extends AppCompatActivity {
    private Hero hero;
    private String description, name, superpower;
    private int rank, resourceImage;
    private ImageView imageView;
    private TextView descView, nameView, supView, rankView;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        Intent lastIntent = getIntent();
        hero = lastIntent.getParcelableExtra(HeroesListActivity.POSITION);
        description = hero.getDescription();
        name = hero.getName();
        rank = hero.getRank();
        superpower = hero.getSuperpower();
        wireWidgets();
        resourceImage = getResources().getIdentifier(hero.getImage(), "drawable", getPackageName());
        imageView.setImageDrawable(getResources().getDrawable(resourceImage));
        descView.setText(description);
        nameView.setText((name));
        rankView.setText(rank + "");
        supView.setText(superpower);
        setListeners();
    }

    public void wireWidgets(){
        descView = findViewById(R.id.textView_heroDetail_description);
        nameView = findViewById(R.id.textView_hero_detail_name);
        supView = findViewById(R.id.textView_heroDetail_superpower);
        rankView = findViewById(R.id.textView_heroDetail_rank);
        imageView = findViewById(R.id.imageView_hero_detail_image);
        back = findViewById(R.id.button_hero_detail_back);
    }

    public void setListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent targetIntent = new Intent(HeroDetailActivity.this, HeroesListActivity.class);
                startActivity(targetIntent);
                finish();
            }
        });
    }

}
