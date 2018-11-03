package com.french.flash_cards.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import android.util.Log;

import com.example.common.fulldialog.contracts.FullScreenDialogFragment;
import com.example.common.fulldialog.fragment.SurnameFragment;
import com.french.flash_cards.R;
import com.french.flash_cards.adapter.ViewPagerAdapter;
import com.french.flash_cards.fragment.ChatFragment;
import com.french.flash_cards.fragment.ContactsFragment;
import com.french.flash_cards.fragment.HomeFragment;
import com.french.flash_cards.utils.BottomNavigationBehavior;
import com.learnfrench.autoplay.AutoPlayFragment;
import com.learnfrench.views.shimmer.fragment.CategoryFragment;


public class MainActivity extends AppCompatActivity implements  FullScreenDialogFragment.OnConfirmListener,
        FullScreenDialogFragment.OnDiscardListener,
        FullScreenDialogFragment.OnDiscardFromExtraActionListener{

    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;
    final String dialogTag = "dialog";


    //Fragments

    FullScreenDialogFragment dialogFragment;
    CategoryFragment chatFragment;
    HomeFragment homeFragment;
    ContactsFragment contactsFragment;
    ChatFragment chatFragment1;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_latest);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_categories:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_bookmark:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.action_share:

                                dialogFragment =
                                        (FullScreenDialogFragment) getSupportFragmentManager().findFragmentByTag(dialogTag);
                                if (dialogFragment != null) {
                                    dialogFragment.setOnConfirmListener(MainActivity.this);
                                    dialogFragment.setOnDiscardListener(MainActivity.this);
                                    dialogFragment.setOnDiscardFromExtraActionListener(MainActivity.this);
                                }
                                final Bundle args = new Bundle();

                                dialogFragment = new FullScreenDialogFragment.Builder(MainActivity.this)
                                        .setTitle(com.example.common.R.string.Title)
                                        .setConfirmButton(null)
                                        .setOnConfirmListener(MainActivity.this)
                                        .setOnDiscardListener(MainActivity.this)
                                        .setContent(AutoPlayFragment.class, args)
                                        .setExtraActions(com.example.common.R.menu.extra_items)
                                        .setOnDiscardFromActionListener(MainActivity.this)
                                        .build();

                                dialogFragment.show(getSupportFragmentManager(), dialogTag);
                                //viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /*  //Disable ViewPager Swipe

       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        */

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        chatFragment = new CategoryFragment();
        contactsFragment=new ContactsFragment();
        chatFragment1=new ChatFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(chatFragment);
        adapter.addFragment(contactsFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConfirm(@Nullable Bundle result) {

    }

    @Override
    public void onDiscard() {

    }

    @Override
    public void onDiscardFromExtraAction(int actionId, @Nullable Bundle result) {

    }
}
