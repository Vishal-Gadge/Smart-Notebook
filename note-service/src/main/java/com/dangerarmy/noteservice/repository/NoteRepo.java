package com.dangerarmy.noteservice.repository;

import com.dangerarmy.noteservice.model.Note;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepo extends JpaRepository<Note, Long> {

//    @Cacheable(key = "#id", value = "note")
    Optional<Note> findById(@Nonnull Long id);
    Optional<Note> findByTitleAndUserId(String title, Long userId);

//    @CacheEvict(key = "#entity.id", value = "note")
//    @Override
//    <S extends Note> S save(@Nonnull S entity);

    List<Note> findByUserId(Long userid);

}
