package com.dangerarmy.noteservice.service;

import com.dangerarmy.noteservice.dto.NoteReq;
import com.dangerarmy.noteservice.dto.UpdateNoteDto;
import com.dangerarmy.noteservice.model.Note;

import java.util.List;

public interface NoteService {
    void addNote(NoteReq req, Long userId);
    Note getNote(String title, Long userId);
    List<Note> getNotes(Long userId);
    void updateNote(UpdateNoteDto req, Long userId);
    void deleteNote(String title, Long userId);
}