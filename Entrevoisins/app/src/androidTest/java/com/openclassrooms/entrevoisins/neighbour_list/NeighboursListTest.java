
package com.openclassrooms.entrevoisins.neighbour_list;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {


    private static int ITEMS_COUNT = 12;
    private NeighbourApiService service;
    private ListNeighbourActivity mActivity;
    private List<Neighbour> mList;
    private String Name = "Caroline";



    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        service = DI.getNewInstanceApiService();
        mList = service.getNeighbours() ;

    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test
    public void myNeighboursList_onNeighbourClick() {
        // get neighbour list then click the first neighbour
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check if id is present in new view
        onView(withId(R.id.toolbar_layout_extended))
                .check(matches(isDisplayed()));

    }

    @Test
    public void myNeighboursList_onCheckFavButtonNeighbour() {
        // get neighbour list then click the first neighbour
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check if id is present in new view
        onView(withId(R.id.toolbar_layout_extended))
                .check(matches(isDisplayed()));
        // perform click on floatingbutton for favorite
        onView(withId(R.id.toggle_fav_button)).perform(click());
        //check if floating button isEnabled
        onView(withId(R.id.toggle_fav_button)).check(matches(isEnabled()));


    }

    @Test
    public void myNeighboursList_onCheckFavNeighbourFromList() {
        // get neighbour list then click the first neighbour
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check if id is present in new view
        onView(withId(R.id.toolbar_layout_extended))
                .check(matches(isDisplayed()));
        // perform click on floatingbutton for favorite
        onView(withId(R.id.toggle_fav_button))
                .perform(click())
                .check(matches(isEnabled()))
                .perform(pressBack());
        // swipeLeft main_content to Favorites
        onView(withId(R.id.main_content))
                .perform(swipeLeft());
        // perform click on Name = Caroline in Favorites
        onView(allOf(withText(Name), isDisplayed()));


    }


    @Test
    public void myNeighboursList_onCheckNeighbourName () {

        // get neighbour list then click the first neighbour
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // check if id is present in new view
        onView(withId(R.id.toolbar_layout_extended))
                .check(matches(isDisplayed()));
        // check if avatar name is 0 = Caroline
        onView(withId(R.id.avatar_name))
                .check(matches(withText(mList.get(0).getName())));
    }

}

