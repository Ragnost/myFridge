package jbbk.myfridge;

import android.graphics.drawable.Icon;

//shoppingmemo
public class DatabaseHelper {

    public String DATABASE_NAME;

    public int ID;
    public String name;
    public String ablaufdatum;
    public String stueckzahl;
    public Icon image;

    public DatabaseHelper(){

    }
    public DatabaseHelper( int ID, String name, String ablaufdatum, String stueckzahl){
        this.ID = ID;
        this.name = name;
        this.ablaufdatum = ablaufdatum;
        this.stueckzahl = stueckzahl;
        this.image = image;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
