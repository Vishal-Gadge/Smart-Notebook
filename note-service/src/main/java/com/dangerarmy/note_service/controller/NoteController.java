package com.dangerarmy.note_service.controller;

import com.dangerarmy.note_service.dto.DelNoteDto;
import com.dangerarmy.note_service.dto.NoteReq;
import com.dangerarmy.note_service.model.Note;
import com.dangerarmy.note_service.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addNote(@RequestBody NoteReq note){
        noteService.addNote(note);
        return ResponseEntity.ok(Map.of("message","Note has been saved"));
    }

    @GetMapping("/getnotes")
    public List<Note> getNotes(){
        return noteService.getNotes();
    }

    @PutMapping("/update")
    public void updateNote(@RequestBody NoteReq req){
        noteService.updateNote(req);
    }

    @DeleteMapping("/delete")
    public void deleteNote(@RequestBody DelNoteDto req){
        noteService.deleteNote(req.getTitle());
    }
}
