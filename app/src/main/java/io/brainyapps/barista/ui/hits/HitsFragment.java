package io.brainyapps.barista.ui.hits;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.brainyapps.barista.R;

public class HitsFragment extends Fragment implements HitsContract.View {

    private HitsContract.Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new HitsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hits, container, false);

        return view;
    }

    @Override
    public void setPresenter(HitsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
