package com.dangerarmy.note_service.service;

import com.dangerarmy.note_service.dto.NoteReq;
import com.dangerarmy.note_service.model.Note;

import java.util.List;

public interface NoteService {
    void addNote(NoteReq req);
    List<Note> getNotes();
    void updateNote(NoteReq req);
    void deleteNote(String title);
}