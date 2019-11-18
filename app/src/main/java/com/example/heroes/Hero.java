package com.example.heroes;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Hero implements Parcelable {
    private String name, description, superpower;
    private int ranking;
    private String image;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSuperpower() {
        return superpower;
    }

    public String getImage(){
        return image;
    }

    public Hero() {

    }

    public int getRank() {
        return ranking;
    }

    @Override
    public String toString(){
        return name +" "+ ranking +" " +description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.ranking);
        dest.writeString(this.superpower);
        dest.writeString(this.image);
    }

    protected Hero(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.ranking = in.readInt();
        this.superpower = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Hero> CREATOR = new Parcelable.Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel source) {
            return new Hero(source);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };
}
