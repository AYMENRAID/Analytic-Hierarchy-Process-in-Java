/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahp_gui1;

import java.util.*;

/**
 *
 * @author Un1kalny
 */
public class Kryterium {

    public String nazwa;
    public Kryterium parent;
    public List<Kryterium> listaPodkryteriow;
    public boolean zwazoneKryterium = false;

    public Kryterium() {
        nazwa = "";
        parent = null;
        listaPodkryteriow = null;
    }
    public Kryterium(String nazwa, Kryterium parent, List<Kryterium> listaPodkryteriow) {
        this.nazwa = nazwa;
        this.parent = parent;
        this.listaPodkryteriow = listaPodkryteriow;
    }

    public Kryterium(String nazwa) {
        this.nazwa = nazwa;
        parent = null;
        listaPodkryteriow = null;
    }

    public Kryterium(String nazwa, Kryterium parent) {
        this.nazwa = nazwa;
        this.parent = parent;
        listaPodkryteriow = null;
    }

    @Override
    public String toString() {
        String p;
        List<Kryterium> listTmp = new ArrayList<>();
        if(parent == null)
            p = "brak";
        else
            p = parent.nazwa;
        if(listaPodkryteriow != null)
            listTmp = listaPodkryteriow;
        //return "Nazwa: " + nazwa + ", rodzic: " + p + ", listaPodkryteriow: " + listTmp.toString() + ".\n";
        return nazwa;
    }

}
