package com.dangerarmy.authservice.controller;

import com.dangerarmy.authservice.client.NoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteClient noteClient;

    @GetMapping("/showNoteBook")
    public void showNoteBook(){
        noteClient.showNoteHomePage();
    }
}