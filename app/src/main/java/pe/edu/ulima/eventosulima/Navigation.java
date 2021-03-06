package pe.edu.ulima.eventosulima;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import pe.edu.ulima.eventosulima.layout.Asistir;
import pe.edu.ulima.eventosulima.layout.General;
import pe.edu.ulima.eventosulima.layout.Industrial;
import pe.edu.ulima.eventosulima.layout.Sistemas;

public class Navigation extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    ActionBar actionBar;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        sp = getSharedPreferences("preferencias", MODE_PRIVATE);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Obtener referencia al action bar

        actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_slideshow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.flaContenido, new General());
        transaction.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);

                FragmentManager fm = getSupportFragmentManager();

                FragmentTransaction transaction = fm.beginTransaction();
                if(item.getItemId() == R.id.fragmentgeneral){
                    transaction.replace(R.id.flaContenido, new General());
                }else if(item.getItemId() == R.id.fragmentindustrial){
                    transaction.replace(R.id.flaContenido, new Industrial());
                }else if(item.getItemId() == R.id.fragmentsistemas){
                    transaction.replace(R.id.flaContenido, new Sistemas());
                }else if(item.getItemId() == R.id.miseventos){
                    transaction.replace(R.id.flaContenido, new Asistir());
                }

                transaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.getMenuInflater().inflate(R.menu.alarm_context_menu, menu);
        menu.setHeaderTitle("Opciones de Alarma");
        int intOpcion  = sp.getInt("opcion",0);
        if(intOpcion>0) {
            MenuItem mi = menu.findItem(intOpcion);
            mi.setChecked(true);
        } else {
            MenuItem mi = menu.findItem(R.id.mi0);
            mi.setChecked(true);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();
        switch (selectedItem) {
            case R.id.mi0:
            case R.id.mi5:
            case R.id.mi10:
            case R.id.mi15:
            case R.id.mi30:
                if(item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("opcion", selectedItem);
                editor.commit();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu1:
                Intent intent = new Intent(this, CalendarioActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "Accion 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                //Toast.makeText(this, "Accion 2", Toast.LENGTH_SHORT).show();
                registerForContextMenu(findViewById(R.id.butInvisible));
                openContextMenu(findViewById(R.id.butInvisible));

                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
