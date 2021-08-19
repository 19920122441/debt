package com.zhichangan.debt.note.mapper;

import com.zhichangan.debt.note.entity.Note;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteMapper {
    int deleteByPrimaryKey(String id);

    int insert(Note record);

    int insertSelective(Note record);

    Note selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKey(Note record);

    List<Note> queryNoteByLawCaseId(String lawCaseId);

    Integer count(String lawCaseId);

    List<Note> getNotePageListByLawCaseId(@Param("lawCaseId") String lawCaseId, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    int deleteByLawCaseId(String lawCaseId);

    List<Note> queryUserWork(String name, String now);
}