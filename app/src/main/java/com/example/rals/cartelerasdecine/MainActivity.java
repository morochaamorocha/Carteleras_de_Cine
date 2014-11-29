package com.example.rals.cartelerasdecine;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity implements SearchView.OnQueryTextListener{
    private ListView listaCines;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private final String[] cines = new String[]{"Plaza Mar 2", "Kinépolis", "Yelmo Cines"};
    static ArrayList<Pelicula> cartelera;
    private final String URL_RSS = "http://rss.sensacine.com/cine/encartelera?format=xml";
    private SearchManager searchManager;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        listaCines = (ListView)findViewById(R.id.menu_lateral);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.abrir_menu, R.string.cerrar_menu){
            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.titulo_menu);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        listaCines.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cines));
        listaCines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //seleccionarCine(position);
                Toast.makeText(MainActivity.this, "Has presionado la opción " + position, Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(listaCines);
            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView)menu.findItem(R.id.buscar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        boolean drawerAbierto = drawerLayout.isDrawerOpen(listaCines);
        menu.findItem(R.id.actualizar).setVisible(!drawerAbierto);
        menu.findItem(R.id.buscar).setVisible(!drawerAbierto);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){
            case R.id.actualizar:
                /*TODO: Actualizar contenido del fragment*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void seleccionarCine(int position){

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new FragmentCartelera())
                .commit();

        listaCines.setItemChecked(position, true);
        getActionBar().setTitle(cines[position]);
        drawerLayout.closeDrawer(listaCines);
    }

    private void leerRSS(String RSS){
        XmlPullParser parser = Xml.newPullParser();
        int evento, item = 0;
        boolean aux = false;
        Pelicula pelicula = null;
        cartelera = new ArrayList<Pelicula>();

        try {
            parser.setInput(new StringReader(RSS));
            evento = parser.getEventType();

            while (evento != XmlPullParser.END_DOCUMENT && item <= 5){

                switch (evento){
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")){
                            aux = true;
                            pelicula = new Pelicula();
                        }
                        else if (aux && parser.getName().equals("title")){
                            pelicula.setTitulo(parser.nextText());
                        }
                        else if (aux && parser.getName().equals("description")){
                            pelicula.setSinopsis(parser.nextText());
                        }
                        else if (aux && parser.getName().equals("enclosure")){
                            pelicula.setUrlImg(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")){

                            cartelera.add(pelicula);
                            descargarImagen(pelicula.getUrlImg(), item);

                            item++;
                        }
                        break;

                }
                evento = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void descargarImagen(String url, int p){

        InputStream stream = null;
        try {

            URL url1 = new URL(url);
            stream = url1.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            cartelera.get(p).setCartel(bitmap);

            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String descargarRSS(){

        InputStream stream = null;
        String rss = null;

        try {
            URL url = new URL(URL_RSS);
            stream = url.openStream();
            rss = stream.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rss;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)){
            /*TODO: Limpiar el filtro de busqueda*/
        }
        else{
            /*TODO: filtrar la lista*/
        }
        return true;
    }
}
