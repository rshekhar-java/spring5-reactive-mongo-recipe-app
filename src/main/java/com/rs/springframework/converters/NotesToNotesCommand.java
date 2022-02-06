package com.rs.springframework.converters;

import com.rs.springframework.commands.NotesCommand;
import com.rs.springframework.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * created by rs 1/10/2022.
 */
@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Override
    public NotesCommand convert(Notes source) {
        if(source == null){
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}
