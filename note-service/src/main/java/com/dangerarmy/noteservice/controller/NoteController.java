package com.dangerarmy.noteservice.controller;

import com.dangerarmy.noteservice.dto.DelNoteDto;
import com.dangerarmy.noteservice.dto.GetNoteDto;
import com.dangerarmy.noteservice.dto.NoteReq;
import com.dangerarmy.noteservice.dto.UpdateNoteDto;
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
    public ResponseEntity<Map<String, String>> addNote(@RequestBody NoteReq noteReq,@RequestHeader("X-User-Id") Long userId){
        noteService.addNote(noteReq, userId);
        return ResponseEntity.ok(Map.of("message","Note has been saved"));
    }

    @PostMapping("/getNote")
    public Note getNote(@RequestBody GetNoteDto noteDto, @RequestHeader("X-User-Id") Long userId){
        return noteService.getNote(noteDto.getTitle(), userId);
    }

    @GetMapping("/getNotes")
    public List<Note> getNotes(@RequestHeader("X-User-Id") Long userId){
        return noteService.getNotes(userId);
    }

    @PutMapping("/update")
    public void updateNote(@RequestBody UpdateNoteDto req, @RequestHeader("X-User-Id") Long userId){
        noteService.updateNote(req, userId);
    }

    @DeleteMapping("/delete")
    public void deleteNote(@RequestBody DelNoteDto req, @RequestHeader("X-User-Id") Long userId){
        noteService.deleteNote(req.getTitle(), userId);
    }
}
