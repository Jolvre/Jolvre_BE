package com.example.jolvre.group.api;

import com.example.jolvre.group.service.GroupExhibitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupExhibitController {
    private final GroupExhibitService groupExhibitService;

    @PostMapping
    public void create() {
        groupExhibitService.createGroupExhibit(1L);
    }
}
