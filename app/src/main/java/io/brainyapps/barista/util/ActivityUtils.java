package io.brainyapps.barista.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {

    public static void replaceFragmentInContainer(int resId,
                                                  FragmentManager fragmentManager,
                                                  Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.replace(resId, fragment);

        transaction.commitAllowingStateLoss();
    }
}
