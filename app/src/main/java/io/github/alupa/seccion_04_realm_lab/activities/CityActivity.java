package io.github.alupa.seccion_04_realm_lab.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import io.github.alupa.seccion_04_realm_lab.R;
import io.github.alupa.seccion_04_realm_lab.adapters.CityAdapter;
import io.github.alupa.seccion_04_realm_lab.models.City;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CityActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<City>> {

    private Realm realm;
    private FloatingActionButton fab;

    private RealmResults<City> cities;

    private RecyclerView recyclerView;
    private CityAdapter adapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        realm = Realm.getDefaultInstance();
        cities = realm.where(City.class).findAll();
        cities.addChangeListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlayoutManager);

        fab = (FloatingActionButton) findViewById(R.id.fabAddCity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityActivity.this, AddEditCityActivity.class);
                startActivity(intent);
            }
        });

        setHideShowFAB();

        adapter = new CityAdapter(cities, R.layout.recycler_view_city_item, new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city, int position) {
                Intent intent = new Intent(CityActivity.this, AddEditCityActivity.class);
                intent.putExtra("id", city.getId());
                startActivity(intent);
            }
        }, new CityAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(City city, int position) {
                showAlertForRemovingCity("Delete city", "Are you sure you want to delete " + city.getName() + "?", position);
            }
        });

        recyclerView.setAdapter(adapter);
        cities.addChangeListener(this);
    }

    private void showAlertForRemovingCity(String title, String message, final int position) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCity(position);
                        Toast.makeText(CityActivity.this, "It has been deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", null).show();
    }

    private void deleteCity(int position) {
        realm.beginTransaction();
        cities.get(position).deleteFromRealm();
        realm.commitTransaction();
    }

    private void setHideShowFAB() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });
    }

    @Override
    public void onChange(RealmResults<City> element) {
        adapter.notifyDataSetChanged();
    }
}