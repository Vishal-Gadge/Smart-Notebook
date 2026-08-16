package com.dangerarmy.noteservice.service.Impl;

import com.dangerarmy.noteservice.dto.NoteReq;
import com.dangerarmy.noteservice.dto.UpdateNoteDto;
import com.dangerarmy.noteservice.exception.AlreadyExistException;
import com.dangerarmy.noteservice.exception.NullPointerException;
import com.dangerarmy.noteservice.exception.OutOfLimitExecption;
import com.dangerarmy.noteservice.model.Note;
import com.dangerarmy.noteservice.repository.NoteRepo;
import com.dangerarmy.noteservice.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;

    @Override
    public void addNote(NoteReq noteReq, Long userId) {
        if(noteReq == null){
            throw new NullPointerException("Note is empty");
        } else if (noteReq.getTitle() == null) {
            throw new NullPointerException("Title cannot be empty");
        } else if (noteReq.getTitle().length() > 300){
            throw new OutOfLimitExecption("Title is too big, max 300 characters");
        }

        System.out.println("title length is : "+noteReq.getTitle().length());

        Optional<Note> note = noteRepo.findByTitleAndUserId(noteReq.getTitle(), userId);

        if(note.isPresent()){
            throw new AlreadyExistException("Note with title : "+noteReq.getTitle()+" already exist");
        }

        noteRepo.save(new Note(
                null,
                userId,
                noteReq.getTitle(),
                noteReq.getText()
        ));
    }

    @Override
    public Note getNote(String title, Long userId){
        if(title == null){
            throw new NullPointerException("Title cannot be empty");
        }
        return noteRepo.findByTitleAndUserId(title, userId)
                .orElseThrow(() -> new NullPointerException("Note for title : "+title+" doesn't exist"));
    }

    @Override
    public List<Note> getNotes(Long userId) {
        return noteRepo.findByUserId(userId);
    }

    @Override
    public void updateNote(UpdateNoteDto req, Long userId) {
        Note note = noteRepo.findByTitleAndUserId(req.getOldTitle(), userId)
                .orElseThrow(() -> new RuntimeException("note for that title doesn't exist"));

        note.setTitle(req.getNewTitle());
        note.setText(req.getNewText());
        noteRepo.save(note);
    }

    @Override
    public void deleteNote(String title, Long userId) {
        Note note = noteRepo.findByTitleAndUserId(title, userId)
                .orElseThrow(() -> new RuntimeException("note with that title doesn't exist"));

        noteRepo.delete(note);
    }
}
