package com.dangerarmy.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "note-service")
public interface NoteClient {

    @GetMapping("/homepage")
    void showNoteHomePage();
}
