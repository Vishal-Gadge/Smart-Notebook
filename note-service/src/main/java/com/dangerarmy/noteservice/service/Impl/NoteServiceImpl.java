package com.dangerarmy.noteservice.service.Impl;

import com.dangerarmy.noteservice.dto.NoteReq;
import com.dangerarmy.noteservice.model.Note;
import com.dangerarmy.noteservice.repository.NoteRepo;
import com.dangerarmy.noteservice.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;

    @Override
    public void addNote(NoteReq noteReq, Long userId) {
        if(noteReq == null){
            throw new RuntimeException();
        }

        noteRepo.save(new Note(
                null,
                userId,
                noteReq.getTitle(),
                noteReq.getText()
        ));
    }

    @Override
    public List<Note> getNotes(Long userId) {
        return noteRepo.findByUserId(userId);
    }

    @Override
    public void updateNote(NoteReq req, Long userId) {
        Note note = noteRepo.findByTitleAndUserId(req.getTitle(), userId)
                .orElseThrow(() -> new RuntimeException("note for that title doesn't exist"));

        note.setText(req.getText());
        noteRepo.save(note);
    }

    @Override
    public void deleteNote(String title, Long userId) {
        Note note = noteRepo.findByTitleAndUserId(title, userId)
                .orElseThrow(() -> new RuntimeException("note with that title doesn't exist"));

        noteRepo.delete(note);
    }
}
