package com.dangerarmy.noteservice.service;

import com.dangerarmy.noteservice.dto.NoteReq;
import com.dangerarmy.noteservice.model.Note;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface NoteService {
    void addNote(NoteReq req, Long userId);
    List<Note> getNotes(Long userId);
    void updateNote(NoteReq req, Long userId);
    void deleteNote(String title, Long userId);
}