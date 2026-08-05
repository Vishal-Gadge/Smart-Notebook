package com.dangerarmy.noteservice.service;

import com.dangerarmy.noteservice.dto.NoteReq;
import com.dangerarmy.noteservice.model.Note;

import java.util.List;

public interface NoteService {
    void addNote(NoteReq req);
    List<Note> getNotes();
    void updateNote(NoteReq req);
    void deleteNote(String title);
}