
package com.learnfrench.autoplay;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.R;
import com.example.common.fulldialog.contracts.FullScreenDialogContent;
import com.example.common.fulldialog.contracts.FullScreenDialogController;


public class AutoPlayFragment extends Fragment implements FullScreenDialogContent {

    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String RESULT_FULL_NAME = "RESULT_FULL_NAME";

    private FullScreenDialogController dialogController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.surname_fragment, container, false);
    }

    @Override
    public void onDialogCreated(final FullScreenDialogController dialogController) {
        this.dialogController = dialogController;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView name = (TextView) getView().findViewById(R.id.name);
        name.setText(getString(R.string.hi_name, getArguments().getString(EXTRA_NAME)));


    }

    @Override
    public boolean onConfirmClick(FullScreenDialogController dialog) {
        Bundle result = new Bundle();
        result.putString(RESULT_FULL_NAME, getArguments().getString(EXTRA_NAME));
        dialog.confirm(result);
        return true;
    }

    @Override
    public boolean onDiscardClick(FullScreenDialogController dialog) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.discard_confirmation_title)
                .setMessage(R.string.discard_confirmation_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogController.discard();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing to do
                    }
                }).show();

        return true;
    }

    @Override
    public boolean onExtraActionClick(MenuItem actionItem, FullScreenDialogController dialogController) {
        Toast.makeText(getContext(), actionItem.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }
}