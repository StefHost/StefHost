package nl.stefhost.stefhost;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Beginscherm extends AppCompatActivity {

    private android.support.v4.widget.DrawerLayout DrawerLayout;
    private android.widget.ListView ListView;
    private ActionBarDrawerToggle DrawerToggle;
    public int icon;

    int[] icons = new int[]{R.drawable.leeg, R.drawable.centerparcs, R.drawable.walloffame, R.drawable.leeg, R.drawable.leeg, R.drawable.spotashot, R.drawable.leeg, R.drawable.leeg, R.drawable.icon};
    String[] titles = new String[] {"Websites", "Center Parcs", "Wall Of Fame", " ", "Apps", "Spot a Shot", " ", "Overige", "Facturen"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginscherm);

        DrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerToggle = new ActionBarDrawerToggle(this,DrawerLayout,0,0) {};
        DrawerLayout.setDrawerListener(DrawerToggle);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        List<HashMap<String,String>> List = new ArrayList<>();

        for(int i=0; i < titles.length; i++){
            HashMap<String,String> HashMap = new HashMap<>();
            HashMap.put("icon", Integer.toString(icons[i]) );
            HashMap.put("title", titles[i]);
            List.add(HashMap);
        }

        String[] van = { "icon", "title" };
        int[] naar = { R.id.imageView, R.id.textView};

        SimpleAdapter SimpleAdapter = new SimpleAdapter(getBaseContext(), List, R.layout.listview_home, van, naar);
        ListView = (ListView) findViewById(R.id.list_view);
        ListView.setAdapter(SimpleAdapter);
        ListView.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setTitle("   StefHost");
        getSupportActionBar().setIcon(ContextCompat.getDrawable(this, R.drawable.icon));

        DrawerLayout.openDrawer(ListView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return DrawerToggle.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        DrawerToggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String keuze = titles[position];
            selectItem(keuze);
        }
    }

    private void selectItem(String keuze) {

        Log.d("StefHost", "Keuze: "+keuze);

        Fragment fragment = null;

        if (!keuze.equals("Websites") && !keuze.equals("Apps") && !keuze.equals("Overige") && !keuze.equals(" ")) {

            switch (keuze) {
                case "Center Parcs":
                    fragment = new CenterParcs();
                    icon = R.drawable.centerparcs;
                    break;
                case "Wall Of Fame":
                    fragment = new WallOfFame();
                    icon = R.drawable.walloffame;
                    break;
                case "Spot a Shot":
                    fragment = new SpotAShot();
                    icon = R.drawable.spotashot;
                    break;
                case "Facturen":
                    fragment = new Facturen();
                    icon = R.drawable.icon;
                    break;
            }

            DrawerLayout.closeDrawer(ListView);
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("   " + keuze);
            }
            getSupportActionBar().setIcon(ContextCompat.getDrawable(this, icon));
        }
    }

    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("NUMMER");

        if (fragment != null){
            super.onBackPressed();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(Beginscherm.this);
            builder.setTitle("Afsluiten")
                    .setMessage("Weet je zeker dat je de StefHost App wilt afsluiten?")
                    .setCancelable(false);
            builder.setPositiveButton("JA", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton("NEE", null);
            builder.show();
        }
    }

}