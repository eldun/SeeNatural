package com.dunneev.seenatural.Fragments.Reading;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoViewModel;
import com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem;
import com.dunneev.seenatural.Fragments.Staff.StaffViewModel;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import kotlin.NotImplementedError;

import static com.dunneev.seenatural.Enums.KeySignature.C_MAJOR;
import static com.dunneev.seenatural.Enums.PianoNote.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ReadingViewModelTest {

    ReadingViewModel viewModel;
    StaffViewModel staffViewModel;
    PianoViewModel pianoViewModel;

    // Used for testing LiveData
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Mock
    Observer<PianoNote> correctKeyPressedObserverMock;

    @Mock
    Observer<PianoNote> incorrectKeyPressedObserverMock;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new ReadingViewModel();
        staffViewModel = new StaffViewModel();
        pianoViewModel = new PianoViewModel();
        viewModel.setKeySignature(C_MAJOR);
        viewModel.setGenerateAccidentals(true);
        viewModel.setGenerateSharps(true);
        viewModel.setGenerateNaturals(true);
        viewModel.setGenerateFlats(true);

        viewModel.getMutableLiveDataCorrectKeyPressed().observeForever(correctKeyPressedObserverMock);
        viewModel.getMutableLiveDataIncorrectKeyPressed().observeForever(incorrectKeyPressedObserverMock);

    }

    @After
    public void tearDown() {
        viewModel = null;
        staffViewModel = null;
        pianoViewModel = null;
        correctKeyPressedObserverMock = null;
        incorrectKeyPressedObserverMock = null;
    }

    @Test
    public void OnCorrectKeyPressed_CorrectKeyPress_NotifiesObservers() {
        viewModel.onCorrectKeyPressed(C4);

        verify(correctKeyPressedObserverMock).onChanged(C4);

    }

    @Test
    public void OnIncorrectKeyPressed_IncorrectKeyPress_NotifiesObservers() {
        viewModel.onIncorrectKeyPressed(C4);

        verify(incorrectKeyPressedObserverMock).onChanged(C4);

    }

    @Test
    public void getAllNotesInStaffPracticeRangeDescending_MaxRange_ReturnsAllNotes() {
        viewModel.setHighPracticeNote(HIGHEST_NOTE);
        viewModel.setLowPracticeNote(LOWEST_NOTE);

        List<PianoNote> allNotesInRange = viewModel.getAllNotesInStaffPracticeRangeInclusive();

        for (PianoNote note : PianoNote.values()) {
            assertThat(allNotesInRange.contains(note), is(true));
        }
    }


    @Test
    public void IsCorrectPress_SingleOctaveModeTrue_ReturnsCorrectly () {
        StaffPracticeItem item = new StaffPracticeItem(viewModel.getKeySignature(), C_SHARP_4, 0);

        viewModel.setIsSingleOctaveMode(true);

        assertTrue(viewModel.isCorrectPress(C_SHARP_3, item));
        assertTrue(viewModel.isCorrectPress(C_SHARP_4, item));
        assertTrue(viewModel.isCorrectPress(C_SHARP_5, item));

        assertTrue(viewModel.isCorrectPress(D_FLAT_3, item));
        assertTrue(viewModel.isCorrectPress(D_FLAT_4, item));
        assertTrue(viewModel.isCorrectPress(D_FLAT_5, item));

        assertFalse(viewModel.isCorrectPress(D5, item));
        assertFalse(viewModel.isCorrectPress(B2, item));


    }

    @Test
    public void IsCorrectPress_SingleOctaveModeFalse_ReturnsCorrectly () {
        StaffPracticeItem item = new StaffPracticeItem(viewModel.getKeySignature(), C_SHARP_4, 0);

        viewModel.setIsSingleOctaveMode(false);

        assertFalse(viewModel.isCorrectPress(C_SHARP_3, item));
        assertTrue(viewModel.isCorrectPress(C_SHARP_4, item));
        assertFalse(viewModel.isCorrectPress(C_SHARP_5, item));

        assertFalse(viewModel.isCorrectPress(D_FLAT_3, item));
        assertTrue(viewModel.isCorrectPress(D_FLAT_4, item));
        assertFalse(viewModel.isCorrectPress(D_FLAT_5, item));

        assertFalse(viewModel.isCorrectPress(D5, item));
        assertFalse(viewModel.isCorrectPress(B2, item));


    }







    @Test
    public void GeneratePracticableNoteList_GenerateAccidentalsFalse_DoesNotGenerateAccidentals() {
        viewModel.setHighPracticeNote(HIGHEST_NOTE);
        viewModel.setLowPracticeNote(LOWEST_NOTE);

        viewModel.setGenerateAccidentals(false);

        viewModel.generatePracticableNoteList();

        for (PianoNote note : PianoNote.values()) {
            if (PianoNote.isAccidental(note, viewModel.getKeySignature()))
                assertThat(viewModel.getPracticableNotes().contains(note), is(false));
        }
    }

    @Test
    public void GeneratePracticableNoteList_GenerateSharpsFalse_DoesNotGenerateSharps() {
        viewModel.setHighPracticeNote(HIGHEST_NOTE);
        viewModel.setLowPracticeNote(LOWEST_NOTE);

        viewModel.setGenerateSharps(false);

        viewModel.generatePracticableNoteList();

        for (PianoNote note : PianoNote.values()) {
            if (PianoNote.isAccidental(note, viewModel.getKeySignature()) && note.isSharp)
                assertThat(viewModel.getPracticableNotes().contains(note), is(false));
        }
    }

    @Test
    public void GeneratePracticableNoteList_GenerateNaturalsFalse_DoesNotGenerateNaturals() {
        viewModel.setHighPracticeNote(HIGHEST_NOTE);
        viewModel.setLowPracticeNote(LOWEST_NOTE);

        viewModel.setGenerateNaturals(false);

        viewModel.generatePracticableNoteList();

        for (PianoNote note : PianoNote.values()) {
            if (PianoNote.isAccidental(note, viewModel.getKeySignature()) && note.isNatural)
                assertThat(viewModel.getPracticableNotes().contains(note), is(false));
        }
    }

    @Test
    public void GeneratePracticableNoteList_GenerateFlatsFalse_DoesNotGenerateFlats() {
        viewModel.setHighPracticeNote(HIGHEST_NOTE);
        viewModel.setLowPracticeNote(LOWEST_NOTE);

        viewModel.setGenerateFlats(false);

        viewModel.generatePracticableNoteList();

        for (PianoNote note : PianoNote.values()) {
            if (PianoNote.isAccidental(note, viewModel.getKeySignature()) && note.isFlat)
                assertThat(viewModel.getPracticableNotes().contains(note), is(false));
        }
    }


    @Test
    public void GeneratePracticableNoteList_LimitedPlayingRange_OnlyGeneratePlayableNotes() {
        viewModel.setHighPracticeNote(HIGHEST_NOTE);
        viewModel.setLowPracticeNote(LOWEST_NOTE);


        fail();

        }
    }
