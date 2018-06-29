package io.brainyapps.barista.ui.hits;

public interface HitsContract {

    interface View {
        void setPresenter(Presenter presenter);
    }

    interface Adapter {
    }

    interface Presenter {
        void start();
    }
}
