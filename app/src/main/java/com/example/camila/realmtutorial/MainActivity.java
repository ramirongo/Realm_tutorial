package com.example.camila.realmtutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.camila.realmtutorial.Model.Person;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd,btnView,btnDelete;
    private TextView txtView;
    private EditText txtName,txtAge;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        txtName = (EditText)findViewById(R.id.editText);
        txtAge = (EditText)findViewById(R.id.editText2);
        btnView = (Button)findViewById(R.id.button1);
        btnAdd = (Button)findViewById(R.id.button2);
        btnDelete = (Button)findViewById(R.id.button3);
        txtView= (TextView) findViewById(R.id.textView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_on_database(txtName.getText().toString().trim(),Integer.parseInt(txtAge.getText().toString().trim()));
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh_database();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                delete_from_database(name);
            }
        });


    }



    @Override
    protected void onDestroy(){
        super.onDestroy();

        realm.close();
    }

    private void save_on_database(final String name, final int age) {
        /*// Obtain a Realm instance
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        //... add or update objects here ...
        realm.commitTransaction();*/

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.setName(name);
                person.setAge(age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v("Success","----------------->> OK <<------------");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.v("Failed",error.getMessage());
            }
        });

    }

    private void refresh_database() {

       /*RealmResults<User> result = realm.where(User.class)
                .equalTo("name", "John")
                .or()
                .equalTo("name", "Peter")
                .findAllAsync();*/

        RealmResults<Person> result = realm.where(Person.class).findAllAsync();
        result.load();  // be careful, this will block the current thread until it returns

        String output = "";

        for(Person person:result){
            output+=person.toString();
        }

        txtView.setText(output);

    }

    private void delete_from_database(String name) {

        final RealmResults<Person> persons = realm.where(Person.class).equalTo("name",name).findAll();

        // All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // remove single match
                persons.deleteAllFromRealm();
            }
        });

    }
}
