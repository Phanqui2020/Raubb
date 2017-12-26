package cofeas.dev.raubb.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cofeas.dev.raubb.R;

public class About extends AppCompatActivity {

    Toolbar tbabout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tbabout = findViewById(R.id.tbabout);
        actionToolbar();

    }

    private void actionToolbar() {
        setSupportActionBar(tbabout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbabout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
