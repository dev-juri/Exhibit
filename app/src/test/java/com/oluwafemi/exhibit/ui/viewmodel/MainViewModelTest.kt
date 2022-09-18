package com.oluwafemi.exhibit.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oluwafemi.exhibit.data.Exhibit
import com.oluwafemi.exhibit.data.ExhibitLoader
import com.oluwafemi.exhibit.data.FakeRestExhibitLoader
import com.oluwafemi.exhibit.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    private lateinit var fakeRestExhibitLoader: FakeRestExhibitLoader

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        fakeRestExhibitLoader = FakeRestExhibitLoader()
        viewModel = MainViewModel(fakeRestExhibitLoader)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch exhibits returns error`() = runTest() {
        viewModel.clearExhibits()
        fakeRestExhibitLoader.returnError(true)
        viewModel.fetchExhibits()

        val result = viewModel.exhibit.getOrAwaitValue()

        assertEquals(0, result.size)
    }

    @Test
    fun `fetch exhibits returns success`() = runTest() {
        viewModel.clearExhibits()
        fakeRestExhibitLoader.returnError(false)
        viewModel.fetchExhibits()

        val result = viewModel.exhibit.getOrAwaitValue()

        assertEquals(result.size, FakeRestExhibitLoader.dummyExhibitData.size)
    }
}