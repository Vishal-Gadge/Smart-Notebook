package com.dangerarmy.noteservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateNoteDto {
    String oldTitle;
    String newTitle;
    String newText;
}
