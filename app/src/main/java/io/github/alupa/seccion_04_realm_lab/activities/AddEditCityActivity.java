package io.github.alupa.seccion_04_realm_lab.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.github.alupa.seccion_04_realm_lab.R;
import io.github.alupa.seccion_04_realm_lab.models.City;
import io.realm.Realm;

public class AddEditCityActivity extends AppCompatActivity {

    private int cityId;
    private boolean isCreation;

    private Realm realm;
    private City city;

    private ImageView imageViewCity;
    private EditText editTextCityName;
    private EditText editTextCityDescription;
    private EditText editTextCityLink;
    private Button btnPreview;
    private RatingBar ratingBarCity;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_city);

        realm = Realm.getDefaultInstance();
        bindUIPreferences();

        if (getIntent().getExtras() != null){
            isCreation = false;
            cityId = getIntent().getExtras().getInt("id");
            city = getCityById(cityId);
            bindDataToFields();
        } else {
            isCreation = true;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle((isCreation) ? "Create new City" : "Edit City" );

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = editTextCityLink.getText().toString();
                if (link.length() > 0)
                    loadImageLinkForPreview(link);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCity();
            }
        });
    }

    private void bindDataToFields() {
        editTextCityName.setText(city.getName());
        editTextCityDescription.setText(city.getDescription());
        editTextCityLink.setText(city.getImage());
        loadImageLinkForPreview(city.getImage());
        ratingBarCity.setRating(city.getStars());
    }

    private City getCityById(int cityId) {
        return realm.where(City.class).equalTo("id", cityId).findFirst();
    }


    private void SaveCity() {
        if (ValiditingDataForCity()){
            String name = editTextCityName.getText().toString();
            String description = editTextCityDescription.getText().toString();
            String link = editTextCityLink.getText().toString();
            float stars = ratingBarCity.getRating();

            City city = new City(name, description, link, stars);
            // En caso de que sea una edición en vez de creación pasamos el ID
            if (!isCreation) city.setId(cityId);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(city);
            realm.commitTransaction();

            goToMainActivity();
        } else {
            Toast.makeText(this, "The data is not valid, please check the fields again", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean ValiditingDataForCity() {
        return (editTextCityName.getText().toString().length() > 0 &&
                editTextCityDescription.getText().length() > 0 &&
                editTextCityLink.getText().length() > 0) ? true : false;
    }

    private void goToMainActivity() {
        Intent intent = new Intent(AddEditCityActivity.this, CityActivity.class);
        startActivity(intent);
    }

    private void loadImageLinkForPreview(String link) {
        Picasso.with(this).load(link).fit().into(imageViewCity);
    }

    private void bindUIPreferences() {
        imageViewCity = (ImageView) findViewById(R.id.imageViewPreview);
        editTextCityName = (EditText) findViewById(R.id.editTextCityName);
        editTextCityDescription = (EditText) findViewById(R.id.editTextCityDescription);
        editTextCityLink = (EditText) findViewById(R.id.editTextCityImage);
        btnPreview = (Button) findViewById(R.id.buttonPreview);
        ratingBarCity = (RatingBar) findViewById(R.id.ratingBarCity);
        fab = (FloatingActionButton) findViewById(R.id.fabSaveCity);
    }
}
