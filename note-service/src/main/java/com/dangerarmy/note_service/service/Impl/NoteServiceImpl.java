package com.dangerarmy.note_service.service.Impl;

import com.dangerarmy.note_service.dto.NoteReq;
import com.dangerarmy.note_service.model.Note;
import com.dangerarmy.note_service.repository.NoteRepo;
import com.dangerarmy.note_service.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;
    private final JwtServiceImpl jwtService;

    @Override
    public void addNote(NoteReq noteReq) {
        if(noteReq == null){
            throw new RuntimeException();
        }

        noteRepo.save(new Note(
                null,
                jwtService.extractMyUserDetails().getId(),
                noteReq.getTitle(),
                noteReq.getText()
        ));
    }

    @Override
    public List<Note> getNotes() {
        Long userid = jwtService.extractMyUserDetails().getId();
        return noteRepo.findByUserId(userid);
    }

    @Override
    public void updateNote(NoteReq req) {
        Long userid = jwtService.extractMyUserDetails().getId();
        Note note = noteRepo.findByTitleAndUserId(req.getTitle(), userid)
                .orElseThrow(() -> new RuntimeException("note for that title doesn't exist"));

        note.setText(req.getText());
        noteRepo.save(note);
    }

    @Override
    public void deleteNote(String title) {
        Long userid = jwtService.extractMyUserDetails().getId();
        Note note = noteRepo.findByTitleAndUserId(title, userid)
                .orElseThrow(() -> new RuntimeException("note with that title doesn't exist"));

        noteRepo.delete(note);
    }
}
