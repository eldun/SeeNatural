package com.dunneev.seenatural.Fragments.Piano;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.dunneev.seenatural.Enums.PianoNote;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class PianoViewModelTest {

    // Used for testing LiveData
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<PianoNote> keyDownObserverMock;

    @Mock
    private Observer<PianoNote> keyUpObserverMock;

    private PianoViewModel pianoViewModel;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pianoViewModel = new PianoViewModel();

        pianoViewModel.getMutableLiveDataKeyPressed().observeForever(keyDownObserverMock);
        pianoViewModel.getMutableLiveDataKeyReleased().observeForever(keyUpObserverMock);
    }

    @After
    public void tearDown() throws Exception {

        keyDownObserverMock = null;
        keyUpObserverMock = null;
    }

    @Test
    public void SetIsSingleOctaveMode_True_SetsLowAndHighNotes() {

        assertNull(pianoViewModel.getLowNote());
        assertNull(pianoViewModel.getHighNote());

        pianoViewModel.setIsSingleOctaveMode(true);

        assertThat(pianoViewModel.getLowNote(), is(PianoNote.C4));
        assertThat(pianoViewModel.getHighNote(), is(PianoNote.B4));
    }

    @Test
    public void KeyDown_NotesInPlayableRange_NotifiesKeyPressedObserver() {

        verifyZeroInteractions(keyDownObserverMock);
        pianoViewModel.keyDown(PianoNote.C4);
        verify(keyDownObserverMock).onChanged(PianoNote.C4);

        pianoViewModel.keyDown(PianoNote.G4);
        verify(keyDownObserverMock).onChanged(PianoNote.G4);
    }

    @Test
    public void KeyUp_NotesInPlayableRange_NotifiesKeyReleasedObserver() {

        verifyZeroInteractions(keyUpObserverMock);
        pianoViewModel.keyUp(PianoNote.C4);
        verify(keyUpObserverMock).onChanged(PianoNote.C4);

        pianoViewModel.keyUp(PianoNote.G4);
        verify(keyUpObserverMock).onChanged(PianoNote.G4);
    }
}