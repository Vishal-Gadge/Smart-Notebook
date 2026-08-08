package com.dangerarmy.noteservice.controller;

import com.dangerarmy.noteservice.dto.DelNoteDto;
import com.dangerarmy.noteservice.dto.NoteReq;
import com.dangerarmy.noteservice.model.Note;
import com.dangerarmy.noteservice.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addNote(@RequestBody NoteReq note,@RequestHeader("X-User-Id") Long userId){
        noteService.addNote(note, userId);
        return ResponseEntity.ok(Map.of("message","Note has been saved"));
    }

    @GetMapping("/getnotes")
    public List<Note> getNotes(@RequestHeader("X-User-Id") Long userId){
        return noteService.getNotes(userId);
    }

    @PutMapping("/update")
    public void updateNote(@RequestBody NoteReq req, @RequestHeader("X-User-Id") Long userId){
        noteService.updateNote(req, userId);
    }

    @DeleteMapping("/delete")
    public void deleteNote(@RequestBody DelNoteDto req, @RequestHeader("X-User-Id") Long userId){
        noteService.deleteNote(req.getTitle(), userId);
    }
}
