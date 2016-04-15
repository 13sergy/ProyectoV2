package com.demo.slidingmenu_tabhostviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.demo.adapters.NavListAdapter;
import com.demo.fragments.MisEntradas;
import com.demo.fragments.MyAbout;
import com.demo.fragments.MySettings;
import com.demo.models.NavItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

	DrawerLayout drawerLayout;
	RelativeLayout drawerPane;
	ListView lvNav;

	List<NavItem> listNavEntradas;
	List<Fragment> listFragments;

	ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);

		lvNav = (ListView) findViewById(R.id.nav_list);

		entradas();

		// create listener for drawer layout
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.string.drawer_opened, R.string.drawer_closed) {

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
				super.onDrawerClosed(drawerView);
			}

		};

		drawerLayout.setDrawerListener(actionBarDrawerToggle);

	}

	private void entradas() {
		listNavEntradas = new ArrayList<NavItem>();
		listNavEntradas.add(new NavItem("Entradas", "Nuevos datos",
				R.drawable.ic_action_home));
		listNavEntradas.add(new NavItem("Informes", "Datos estadísticos",
				R.drawable.ic_action_about));
		listNavEntradas.add(new NavItem("Gráficos", "",
				R.drawable.ic_action_about));

		listNavEntradas.add(new NavItem("Settings", "Change something",
				R.drawable.ic_action_settings));
		listNavEntradas.add(new NavItem("About", "Author's information",
				R.drawable.ic_action_about));

		NavListAdapter navListAdapterEntradas = new NavListAdapter(
				getApplicationContext(), R.layout.item_nav_list, listNavEntradas);

		lvNav.setAdapter(navListAdapterEntradas);

		listFragments = new ArrayList<Fragment>();
		listFragments.add(new MisEntradas());
		listFragments.add(new MisEntradas());
		listFragments.add(new MySettings());
		listFragments.add(new MyAbout());

		// load first fragment as default:
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.main_content, listFragments.get(0)).commit();

		setTitle(listNavEntradas.get(0).getTitle());
		lvNav.setItemChecked(0, true);
		drawerLayout.closeDrawer(drawerPane);

		// set listener for navigation items:
		lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				// replace the fragment with the selection correspondingly:
				FragmentManager fragmentManager = getSupportFragmentManager();
				fragmentManager
						.beginTransaction()
						.replace(R.id.main_content, listFragments.get(position))
						.commit();

				setTitle(listNavEntradas.get(position).getTitle());
				lvNav.setItemChecked(position, true);
				drawerLayout.closeDrawer(drawerPane);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the MisEntradas/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (actionBarDrawerToggle.onOptionsItemSelected(item))
			return true;

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
	}
}
