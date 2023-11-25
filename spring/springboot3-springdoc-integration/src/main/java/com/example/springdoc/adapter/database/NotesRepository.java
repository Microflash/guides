package com.example.springdoc.adapter.database;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface NotesRepository extends CrudRepository<Note, UUID> {

	List<Note> findAll();

	List<Note> findAllByIdIn(List<UUID> id);

	@Query("""
	insert into notes (title, body, created_by, updated_by, word_count, read_only)
	values (:title, :body, :user, :user, :wordCount, :readOnly) returning *
	""")
	Optional<Note> save(String title, String body, String user, long wordCount, boolean readOnly);

	@Query("""
	update notes
	set title = case when title is distinct from :title then coalesce(:title, title, '') else title end,
			body = case when body is distinct from :body then :body else body end,
			updated_by = case when updated_by is distinct from :user then :user else updated_by end,
			updated_at = localtimestamp,
			version = version + 1,
			word_count = :wordCount,
			read_only = :readOnly
	where id = :id
	and read_only = false
	returning *
	""")
	Optional<Note> update(UUID id, String title, String body, String user, long wordCount, boolean readOnly);

	@Query("delete from notes where id in (:ids) returning *")
	Stream<Note> deleteAllByIdIn(List<UUID> ids);
}
