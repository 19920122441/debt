package com.zhichangan.debt.note.service.impl;

import com.zhichangan.debt.contacts.entity.Contacts;
import com.zhichangan.debt.contacts.service.ContactsService;
import com.zhichangan.debt.lawCase.entity.LawCase;
import com.zhichangan.debt.lawCase.service.LawCaseService;
import com.zhichangan.debt.note.entity.Note;
import com.zhichangan.debt.note.mapper.NoteMapper;
import com.zhichangan.debt.note.service.NoteService;
import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private LawCaseService lawCaseService;
    @Autowired
    private UserService userService;

    @Override
    public List<Note> queryNoteByLawCaseId(String id) {
        List<Note> noteList=noteMapper.queryNoteByLawCaseId(id);
        return noteList;
    }

    //新增一条催收记录，将note信息补全，并且查出对应的联系人信息并返回   ,并且将案件的最后跟进时间更新，并且将下次跟进时间设置为未设置
    @Override
    public Note add(Note note) {
        note.setId(UUID.randomUUID().toString().replace("-",""));
        note.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        noteMapper.insertSelective(note);
        Contacts contacts = contactsService.queryContactsById(note.getContactsId());
        note.setContacts(contacts);
        LawCase lawCase=new LawCase();
        lawCase.setId(note.getLawcaseId());
        lawCase.setLastCallTime(note.getTime());
        lawCase.setNextCallTime("未设置");
        lawCaseService.updateSelective(lawCase);
        return note;
    }
    //统计note个数
    @Override
    public Integer count(String lawCaseId) {
        Integer noteCount=noteMapper.count(lawCaseId);
        return noteCount;
    }
    //note分页查询,捎带脚查联系人，封装到note里
    @Override
    public List<Note> getNotePageListByLawCaseId(String lawCaseId, String pageNumber, String pageSize) {
        Integer startIndex=(Integer.parseInt(pageNumber)-1)*Integer.parseInt(pageSize);
        Integer pageSizeInt=Integer.parseInt(pageSize);

        List<Note> noteList = noteMapper.getNotePageListByLawCaseId(lawCaseId, startIndex, pageSizeInt);
        for (Note note:noteList){
            Contacts contacts = contactsService.queryContactsById(note.getContactsId());
            note.setContacts(contacts);
        }
        return noteList;
    }
    //根据案件id删除催记

    @Override
    public int deleteByLawCaseId(String lawCaseId) {
        int result=noteMapper.deleteByLawCaseId(lawCaseId);
        return result;
    }
    //查询员工当天的工作记录

    @Override
    public List<Map<String, Object>> userWork(String id, String now) {
        List<Map<String,Object>> result=new ArrayList<>();
        //查找到员工
        User user=userService.queryUserById(id);
        //查找员工的所有催记
        List<Note> noteList=noteMapper.queryUserWork(user.getName(),now);
        for(Note note:noteList){
            LawCase lawCase = lawCaseService.queryLawCaseById(note.getLawcaseId());
            Map<String,Object> map=new HashMap<>();
            map.put("lawcase",lawCase);
            map.put("note",note);
            result.add(map);
        }
        return result;
    }
}
