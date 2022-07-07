package com.kevin.breatheezy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.arindam.library.Model.HelpArin;
import com.arindam.library.interfaces.OnItemClickListener;
import com.arindam.library.navigationliveo.NavigationArin;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.activity.about.AboutActivity;
import com.kevin.breatheezy.activity.contacts.ContactsActivity;
import com.kevin.breatheezy.fragment.MyTreatmentFragment;
import com.kevin.breatheezy.preferences.GlobalPreferenceManager;
import com.kevin.breatheezy.util.Constant;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class MainActivity extends NavigationArin implements OnItemClickListener {

    private TextView tvName;
    private TextView mToolbarTitle;
    private HelpArin mHelpArin;
    @Override
    public void onInt(Bundle bundle) {
        View customHeader = getLayoutInflater().inflate(R.layout.drawer_header, this.getListView(), false);
        tvName = customHeader.findViewById(R.id.tvName);

        mHelpArin = new HelpArin();
        mHelpArin.add(getString(R.string.treatment), MaterialDrawableBuilder.with(this).setIcon(MaterialDrawableBuilder.IconValue.HOME).setColor(R.color.colorWhite).build());
        mHelpArin.add(getString(R.string.action_plan), MaterialDrawableBuilder.with(this).setIcon(MaterialDrawableBuilder.IconValue.LIGHTBULB_ON).setColor(R.color.colorWhite).build());
        mHelpArin.add(getString(R.string.control), MaterialDrawableBuilder.with(this).setIcon(MaterialDrawableBuilder.IconValue.SCREWDRIVER).setColor(R.color.colorWhite).build());
        mHelpArin.add(getString(R.string.function), MaterialDrawableBuilder.with(this).setIcon(MaterialDrawableBuilder.IconValue.FUNCTION_VARIANT).setColor(R.color.colorWhite).build());
        mHelpArin.add(getString(R.string.settings), MaterialDrawableBuilder.with(this).setIcon(MaterialDrawableBuilder.IconValue.SETTINGS).setColor(R.color.colorWhite).build());
        mHelpArin.add(getString(R.string.contact), MaterialDrawableBuilder.with(this).setIcon(MaterialDrawableBuilder.IconValue.CONTACTS).setColor(R.color.colorWhite).build());
        mHelpArin.add(getString(R.string.about), MaterialDrawableBuilder.with(this).setIcon(MaterialDrawableBuilder.IconValue.INFORMATION).setColor(R.color.colorWhite).build());

        with(this).startingPosition(0)
                .addAllHelpItem(mHelpArin.getHelp())
                .colorItemSelected(R.color.nliveo_blue_colorPrimary)
                .colorItemDefault(R.color.silver)
                .colorLineSeparator(R.color.almostBlack87)
                .customHeader(customHeader)
                .backgroundList(R.color.colorPrimary)
                .build();

        View toolBar = getLayoutInflater().inflate(R.layout.drawer_toolbar, null);
        mToolbarTitle = toolBar.findViewById(R.id.toolbarTitle);
        getToolbar().addView(toolBar);
    }

    @Override
    public void onItemClick(int position) {
        Fragment mFragment = null;
        FragmentManager mFragmentManager = getSupportFragmentManager();
        switch(position){
            case Constant.TREATMENT:
                mToolbarTitle.setText("My Treatment");
                mFragment = new MyTreatmentFragment();
                break;
            case Constant.ACTION_PLAN:
                startActivity(new Intent(MainActivity.this, ActionPlanActivity.class));
                break;
            case Constant.ASTHMA_CONTROL:
                startActivity(new Intent(MainActivity.this, DiaryActivity.class));
                break;
            case Constant.LUNG_FUNCTION:
                startActivity(new Intent(MainActivity.this, LungFunctionActivity.class));
                break;
            case Constant.SETTINGS:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case Constant.CONTACTS:
                startActivity(new Intent(MainActivity.this, ContactsActivity.class));
                break;
            case Constant.ABOUT:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            default:
        }
        if (mFragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvName.setText(GlobalPreferenceManager.getUserName());
    }
}


