package dev.mflash.guides.jwtauth.repository;

import dev.mflash.guides.jwtauth.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {

}
