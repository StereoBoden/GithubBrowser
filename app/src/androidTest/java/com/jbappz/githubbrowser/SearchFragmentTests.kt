package com.jbappz.githubbrowser

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTests {

    //TODO: Add full automation coverage of app

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSearchRepo() {
        val editTextSearch = onView(withId(R.id.editTextSearch))
            .perform(click())
            .check(matches(isDisplayed()))
        editTextSearch.perform(replaceText("android"), closeSoftKeyboard())

        val buttonSearch = onView(withId(R.id.buttonSearch))
            .perform(click())
            .check(matches(isDisplayed()))
        buttonSearch.perform(click())

        val recyclerViewGithub = onView(withId(R.id.recyclerViewGithub))
            .check(matches(isDisplayed()))
        recyclerViewGithub.perform(swipeUp())
    }
}
