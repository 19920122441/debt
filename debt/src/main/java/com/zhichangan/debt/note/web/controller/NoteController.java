package com.zhichangan.debt.note.web.controller;

import com.zhichangan.debt.note.entity.Note;
import com.zhichangan.debt.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;


    //新增一条催收记录,并且将案件的最后跟进时间更新,并且将下次跟进时间设置为未设置
    @RequestMapping("/note/add")
    @ResponseBody
    public Note noteAdd(String text,String contactsId,String lawCaseId,String name){
        Note note = new Note();
        note.setContactsId(contactsId);
        note.setLawcaseId(lawCaseId);
        note.setText(text);
        note.setUserName(name);
        Note result=noteService.add(note);
        return result;
    }
    //note分页查询,捎带脚查下联系人
    @RequestMapping("/note/getNotePageListByLawCaseId")
    @ResponseBody
    public List<Note> getNotePageListByLawCaseId(String lawCaseId,String pageSize,String pageNumber){
        List<Note> noteList=noteService.getNotePageListByLawCaseId(lawCaseId,pageNumber,pageSize);
        return noteList;
    }
}
