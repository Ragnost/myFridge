package jbbk.myfridge;

import android.graphics.drawable.Icon;


/**
 * Helper Class zum einfachen zwischenspeichern eines Datenbankeintrags mit
 * den Einträgen:
 * -Name
 * -Ablaufdatum
 * -Stueckzahl
 * -Vitality
 **/
public class DatabaseHelper {

    public String DATABASE_NAME;


    private String name;
    private String ablaufdatum;
    private String stueckzahl;
    private int vitaly;
    private Icon image;
    private String deltedName;

    public DatabaseHelper() {

    }

    public DatabaseHelper(String name){
        this.deltedName = name;
    }

    public DatabaseHelper(String name, String ablaufdatum, String stueckzahl, Integer vitaly) {
        this.name = name;
        this.ablaufdatum = ablaufdatum;
        this.stueckzahl = stueckzahl;
        this.vitaly = vitaly;
    }

    /**
     * Getter und Setter Methoden für die verschiednen Objekteigenschaften.
     **/
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
