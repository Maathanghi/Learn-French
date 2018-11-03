package com.learn_french.common.fulldialog.contracts;

import android.view.MenuItem;

/**
 * Interface that the content {@link android.support.v4.app.Fragment} must implement. It allows to control over the container dialog.
 */
public interface FullScreenDialogContent {

    void onDialogCreated(FullScreenDialogController dialogController);


    boolean onConfirmClick(FullScreenDialogController dialogController);


    boolean onDiscardClick(FullScreenDialogController dialogController);


    boolean onExtraActionClick(MenuItem actionItem, FullScreenDialogController dialogController);
}

