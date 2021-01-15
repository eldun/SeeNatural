package com.dunneev.seenatural.Fragments.Piano;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class PianoFragmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void PianoFragment_LaunchInContainer_Works() {

        FragmentScenario scenario = FragmentScenario.launchInContainer(PianoFragment.class);
    }
}