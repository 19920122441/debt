package com.zhichangan.debt.note.service;

import com.zhichangan.debt.note.entity.Note;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public interface NoteService {
    List<Note> queryNoteByLawCaseId(String lawcaseId);

    Note add(Note note);

    Integer count(String lawCaseId);

    List<Note> getNotePageListByLawCaseId(String lawCaseId, String pageNumber, String pageSize);

    int deleteByLawCaseId(String lawCaseId);


    List<Map<String, Object>> userWork(String id, String now);
}
