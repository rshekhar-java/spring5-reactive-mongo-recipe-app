package com.rs.springframework.converters;

import com.rs.springframework.commands.NotesCommand;
import com.rs.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * created by rs 1/11/2022.
 */
public class NotesToNotesCommandTest {

    public static final String ID_VALUE = "1";
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNull() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand notesCommand = converter.convert(notes);

        //then
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }
}