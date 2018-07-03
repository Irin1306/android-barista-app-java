package io.brainyapps.barista.ui.hits;

public class HitsPresenter implements HitsContract.Presenter {

    private HitsContract.View mView;

    public HitsPresenter(HitsContract.View view) {
        mView = view;
        mView.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        //
    }

}