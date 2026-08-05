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
