package eventnotifier.en.en.utils.main.search.users;

import java.util.List;

import eventnotifier.en.en.utils.main.base.BaseFragmentView;
import eventnotifier.en.en.utils.model.Profile;

public interface SearchUsersView extends BaseFragmentView {
    void onSearchResultsReady(List<Profile> profiles);

    void showLocalProgress();

    void hideLocalProgress();

    void showEmptyListLayout();

    void updateSelectedItem();
}
