package io.brainyapps.barista.ui.hits;

import io.brainyapps.barista.data.entity.Drink;


public interface HitsListContract {

    interface View {
        void setAdapter(io.brainyapps.barista.ui.hits.HitsListContract.Adapter adapter);

        void showToast(Drink drink);
    }

    interface Adapter {
        void deleteLastElement();

        void addFirstElement(Drink drink);
    }
}
