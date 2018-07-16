package jbbk.myfridge;

import android.graphics.drawable.Icon;

import java.util.ArrayList;

//shoppingmemo
public class DatabaseHelper {

    public String DATABASE_NAME;


    public String name;
    public String ablaufdatum;
    public String stueckzahl;
    public int vitaly;
    public Icon image;

    public DatabaseHelper() {

    }

    public DatabaseHelper(String name, String ablaufdatum, String stueckzahl, Integer vitaly) {
        this.name = name;
        this.ablaufdatum = ablaufdatum;
        this.stueckzahl = stueckzahl;
        this.vitaly = vitaly;
    }


    public int getVitaly() {
        return vitaly;
    }

    public void setVitaly(int vitaly) {
        this.vitaly = vitaly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAblaufdatum() {
        return ablaufdatum;
    }

    public void setAblaufdatum(String ablaufdatum) {
        this.ablaufdatum = ablaufdatum;
    }

    public String getStueckzahl() {
        return stueckzahl;
    }

    public void setStueckzahl(String stueckzahl) {
        this.stueckzahl = stueckzahl;
    }

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }

    @Override
    public String toString() {
        String output = name;
        return output;
    }



}
