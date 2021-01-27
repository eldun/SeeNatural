package com.dunneev.seenatural.Fragments.Staff;

import android.media.audiofx.Equalizer;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.dunneev.seenatural.CustomException;
import com.dunneev.seenatural.Enums.PianoNote;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Test.None;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.mockito.internal.matchers.Equals;

import java.util.List;

import static com.dunneev.seenatural.Enums.KeySignature.*;
import static com.dunneev.seenatural.Enums.PianoNote.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

public class StaffViewModelTest {

    StaffViewModel viewModel;

    // Used for testing LiveData
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Mock
    Observer<List<StaffPracticeItem>> practiceItemsOnStaffObserverMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new StaffViewModel();
        viewModel.setKeySignature(C_MAJOR);

        viewModel.getMutableLiveDataPracticeItemsOnStaff().observeForever(practiceItemsOnStaffObserverMock);
    }

    @After
    public void tearDown() {
        viewModel = null;
        practiceItemsOnStaffObserverMock = null;
    }

    @Test
    public void CreateStaffViewModel_SetValues_ThrowsNoExceptions() throws CustomException.InvalidNoteRangeException {

        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

    }

    @Test
    public void CreateStaffViewModel_LowAndHighNotesEqual_ThrowsNoExceptions() throws CustomException.InvalidNoteRangeException {

        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C4);

    }

    @Test
    public void CreateStaffViewModel_LowAndHighNotesEquivalent_ThrowsNoExceptions() throws Exception {

        viewModel.setLowStaffNote(C_SHARP_4);

        viewModel.setHighStaffNote(D_FLAT_4);

    }

    @Test
    public void CreateStaffViewModel_StaffNotesSetImpossibleRange_ThrowsNoteRangeException() throws CustomException.InvalidNoteRangeException {

        exceptionRule.expect(CustomException.InvalidNoteRangeException.class);


        viewModel.setLowStaffNote(C6);
        viewModel.setHighStaffNote(C4);

    }

    @Test
    public void AddItemToStaff_NoteInRange_AddsItem() {

        try {
            viewModel.setHighStaffNote(C6);
            viewModel.setLowStaffNote(C4);
        }

        catch (CustomException.InvalidNoteRangeException e) {
        }

        finally {
            viewModel.addItemToStaff(C4);

        }

    }

    @Test
    public void AddNoteItemToStaff_NoteOutsideRange_DoesNotAddItem() {

        try {
            viewModel.setHighStaffNote(C6);
            viewModel.setLowStaffNote(C4);
        }

        catch (CustomException.InvalidNoteRangeException e) {
        }

        finally {
            viewModel.addItemToStaff(C8);
            assertTrue(viewModel.getPracticeItemsOnStaff().isEmpty());
        }

    }

    @Test
    public void AddChordItemToStaff_ChordItemWithNoteOutsideRange_DoesNotGetAddedToItem() {

        try {
            viewModel.setHighStaffNote(C6);
            viewModel.setLowStaffNote(C4);
        }

        catch (CustomException.InvalidNoteRangeException e) {
        }

        finally {
            StaffPracticeItem item = viewModel.addItemToStaff(G5, G7);
            assertThat(viewModel.getPracticeItemsOnStaff().size(), is(equalTo(1)));

            assertTrue(item.containsExactPianoNote(G5));
            assertFalse(item.containsExactPianoNote(G7));
        }

    }



    @Test
    public void OnCorrectNote_OnNoteItem_IncrementsToNextItem() {

        try {
            viewModel.setLowStaffNote(C4);
            viewModel.setHighStaffNote(C6);
        }
        catch (CustomException.InvalidNoteRangeException e) {

        }
        finally {
            StaffPracticeItem item0 = viewModel.addItemToStaff(G5);
            StaffPracticeItem item1 = viewModel.addItemToStaff(B5);


            viewModel.onCorrectNote(G5);

            assertThat(viewModel.getCurrentPracticeItem().type, is(equalTo(StaffPracticeItem.Type.NOTE)));
            assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item1)));
        }
    }


    //todo: figure out the "gameplay loop" of sightReading
    @Test
    public void OnCorrectNote_OnLastNoteItem_DoesNotCrash() throws Exception {
//        exceptionRule = ExpectedException.none();

            viewModel.setLowStaffNote(C4);
            viewModel.setHighStaffNote(C6);



            StaffPracticeItem item1 = viewModel.addItemToStaff(G5);


            viewModel.onCorrectNote(G5);

    }

    @Test
    public void OnCorrectNote_OnChordItemNotComplete_RemainsOnCurrentItem() throws CustomException.InvalidNoteRangeException {
        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

        StaffPracticeItem item = viewModel.addItemToStaff(C4, E4, G4);

        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item)));

        viewModel.onCorrectNote(C4);

        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item)));
        assertThat(viewModel.getCurrentPracticeItem().isComplete, is(false));

    }

    @Test
    public void OnCorrectNote_OnChordItemComplete_IncrementsToNextItem() throws CustomException.InvalidNoteRangeException {
        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

        StaffPracticeItem item0 = viewModel.addItemToStaff(C4, E4, G4);
        StaffPracticeItem item1 = viewModel.addItemToStaff(C4);

        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item0)));

        viewModel.onCorrectNote(C4);
        viewModel.onCorrectNote(E4);
        viewModel.onCorrectNote(G4);

        assertTrue(item0.isComplete);
        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item1)));

    }

    @Test
    public void OnIncorrectNote_IncorrectNotePressedOnItem_AddsIncorrectNoteToItem() throws CustomException.InvalidNoteRangeException {
        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

        StaffPracticeItem item = viewModel.addItemToStaff(C4, E4, G4);

        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item)));

        viewModel.onIncorrectNote(D4);

        assertTrue(item.containsExactPianoNote(D4));
    }

    @Test
    public void OnIncorrectNote_IncorrectNotePressedOnItem_RemainsOnCurrentItem() throws CustomException.InvalidNoteRangeException {
        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

        StaffPracticeItem item0 = viewModel.addItemToStaff(C4, E4, G4);
        StaffPracticeItem item1 = viewModel.addItemToStaff(D4);

        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item0)));

        viewModel.onIncorrectNote(D4);

        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item0)));
    }

    @Test
    public void OnKeyReleased_IncorrectNoteInItem_IncorrectNoteRemoved() throws CustomException.InvalidNoteRangeException {
        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

        StaffPracticeItem item = viewModel.addItemToStaff(C4, E4, G4);

        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item)));

        viewModel.onIncorrectNote(D4);
        assertTrue(item.containsExactPianoNote(D4));

        viewModel.onKeyReleased(D4);
        assertFalse(item.containsExactPianoNote(D4));
    }

    @Test
    public void OnKeyReleased_CorrectNoteInItemButStillIncomplete_NoteMarkedNeutral() throws CustomException.InvalidNoteRangeException {
        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

        StaffPracticeItem item = viewModel.addItemToStaff(C4, E4, G4);

        assertThat(viewModel.getCurrentPracticeItem(), is(equalTo(item)));

        viewModel.onCorrectNote(C4);
        assertThat(item.getExactStaffNote(C4).state, is(equalTo(StaffPracticeItem.NoteState.CORRECT)));

        viewModel.onKeyReleased(C4);
        assertThat(item.getExactStaffNote(C4).state, is(equalTo(StaffPracticeItem.NoteState.NEUTRAL)));
    }


    @Test
    public void IsFirstItemIsLastItem_OneItemOnStaff_ReturnsTrue() throws CustomException.InvalidNoteRangeException {
        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

        StaffPracticeItem item = viewModel.addItemToStaff(C4);

        assertTrue(viewModel.isFirstItem(item));
        assertTrue(viewModel.isLastItem(item));
    }

    @Test
    public void IsFirstItemIsLastItem_MultipleItemsOnStaff_ReturnsAsExpected() throws CustomException.InvalidNoteRangeException {
        viewModel.setLowStaffNote(C4);
        viewModel.setHighStaffNote(C6);

        StaffPracticeItem item0 = viewModel.addItemToStaff(C4);
        StaffPracticeItem item1 = viewModel.addItemToStaff(C4);
        StaffPracticeItem item2 = viewModel.addItemToStaff(C4);
        StaffPracticeItem item3 = viewModel.addItemToStaff(C4);

        assertTrue(viewModel.isFirstItem(item0));
        assertFalse(viewModel.isFirstItem(item1));
        assertFalse(viewModel.isFirstItem(item2));
        assertFalse(viewModel.isFirstItem(item3));

        assertFalse(viewModel.isLastItem(item0));
        assertFalse(viewModel.isLastItem(item1));
        assertFalse(viewModel.isLastItem(item2));
        assertTrue(viewModel.isLastItem(item3));

    }


}