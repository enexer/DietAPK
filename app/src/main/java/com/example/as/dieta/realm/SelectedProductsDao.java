package com.example.as.dieta.realm;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by as on 20.05.2017.
 */

public class SelectedProductsDao {
    private Realm realm;
    private RealmConfiguration realmConfig;

    public SelectedProductsDao(Context context) {
        // inicjalizacja realma
        realmConfig = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();

//        DynamicRealm dRealm = DynamicRealm.getInstance(realmConfig);
//        Log.i("V_",   ""+dRealm.getVersion());
//        boolean delete = dRealm.getVersion() < 42;
//        dRealm.close();
//        if (delete) {
//            Realm.deleteRealm(realmConfig);
//        }


        realm = Realm.getInstance(realmConfig);
    }

    // zamknięcie realma
    public void close() {
        realm.close();
    }

    // wstawienie nowej notatki do bazy danych
    public void insertNote(final SelectedProducts selectedProducts) {
        // operacje zapisu muszą odbywać się w transakcji
        realm.beginTransaction();

        // tworzymy nowy obiekt przy pomocy metody createObject()
        SelectedProductsRealm selectedProductsRealm = realm.createObject(SelectedProductsRealm.class);
        selectedProductsRealm.setId(generateId());
        selectedProductsRealm.setWeight(selectedProducts.getWeight());
        selectedProductsRealm.setDayId(selectedProducts.getDayId());
        selectedProductsRealm.setMealId(selectedProducts.getMealId());
        selectedProductsRealm.setWeight(selectedProducts.getWeight());
        selectedProductsRealm.setProductId(selectedProducts.getProductId());
        selectedProductsRealm.setKcal(selectedProducts.getKcal());
        selectedProductsRealm.setCarbo(selectedProducts.getCarbo());
        selectedProductsRealm.setProtein(selectedProducts.getProtein());
        selectedProductsRealm.setFat(selectedProducts.getFat());

        // commitTransaction() zapisuje stan obiektów realmowych do bazy danych
        // jeśli więc stworzyliśmy nowy lub usunęliśmy stary, to w tym momencie
        // te operacje zostaną odwzorowane w bazie
        realm.commitTransaction();
    }

    // pobranie notatki na podstawie jej id
    public SelectedProducts getNoteById(final int id) {
        // aby pobrać obiekt danej klasy korzystamy z metody where()
        // dodatkowe warunki zapytania definiujemy przy pomocy metod takich jak equalTo()
        // findFirst() lub findAll() to metody, które wykonują zdefiniowane zapytanie
        SelectedProductsRealm selectedProductsRealm = realm.where(SelectedProductsRealm.class).equalTo("id", id).findFirst();
        return new NoteMapper().fromRealm(selectedProductsRealm);
    }

//    // aktualizacja notatki w bazie
//    public void updateNote(final SelectedProducts note) {
//        SelectedProductsRealm noteRealm = realm.where(SelectedProductsRealm.class).equalTo("id", note.getId()).findFirst();
//        realm.beginTransaction();
//        noteRealm.setNoteText(note.getNoteText());
//        realm.commitTransaction();
//    }

    // usunięcie notatki z bazy
    public void deleteSelectedById(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(SelectedProductsRealm.class).equalTo("id", id).findFirst().deleteFromRealm();
            }
        });
    }

    // pobranie wszystkich notatek
    public List<SelectedProducts> getAllNotes() {
        List<SelectedProducts> selectedProductses = new ArrayList<>();
        NoteMapper mapper = new NoteMapper();

        RealmResults<SelectedProductsRealm> all = realm.where(SelectedProductsRealm.class).findAll();

        for (SelectedProductsRealm selectedProductsRealm : all) {
            selectedProductses.add(mapper.fromRealm(selectedProductsRealm));
        }

        return selectedProductses;
    }

    // pobranie wszystkich notatek, które zawierają w treści dany tekst
    public List<SelectedProducts> getNotesLike(String text) {
        List<SelectedProducts> selectedProductses = new ArrayList<>();
        NoteMapper mapper = new NoteMapper();

        RealmResults<SelectedProductsRealm> all = realm.where(SelectedProductsRealm.class)
                .contains("weight", text)
//                .contains("dayId", "1")
                .findAll();

        for (SelectedProductsRealm selectedProductsRealm : all) {
            selectedProductses.add(mapper.fromRealm(selectedProductsRealm));
        }

        return selectedProductses;
    }
    public List<SelectedProductsRealm> getRawProductsLike(String day, String meal) {
       // List<SelectedProducts> notes = new ArrayList<>();
       // NoteMapper mapper = new NoteMapper();

        return realm.where(SelectedProductsRealm.class)
                .contains("dayId", day)
                .contains("mealId", meal)
                .findAll();

//        for (SelectedProductsRealm noteRealm : all) {
//            notes.add(mapper.fromRealm(noteRealm));
//        }


    }
    public int countWeight(String day, String meal) {

        return realm.where(SelectedProductsRealm.class)
                .contains("dayId", day)
                .contains("mealId", meal)
                .sum("weight").intValue();

    }
    public int countKcal(String day, String meal) {

        return realm.where(SelectedProductsRealm.class)
                .contains("dayId", day)
                .contains("mealId", meal)
                .sum("kcal").intValue();

    }
    public int countProtein(String day, String meal) {

        return realm.where(SelectedProductsRealm.class)
                .contains("dayId", day)
                .contains("mealId", meal)
                .sum("protein").intValue();

    }
    public int countCarbo(String day, String meal) {

        return realm.where(SelectedProductsRealm.class)
                .contains("dayId", day)
                .contains("mealId", meal)
                .sum("carbo").intValue();

    }
    public int countFat(String day, String meal) {

        return realm.where(SelectedProductsRealm.class)
                .contains("dayId", day)
                .contains("mealId", meal)
                .sum("fat").intValue();

    }


    public List<SelectedProductsRealm> getRawNotes(){
        return realm.where(SelectedProductsRealm.class).findAll();
    }

    // usunięcie wszystkich notatek
    public void deleteAllNotes() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(SelectedProductsRealm.class)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }

    // wygenerowanie nowego id dla notatki
    private int generateId() {
        return realm.where(SelectedProductsRealm.class).max("id").intValue() + 1;
    }
}