package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.models.repository.TempTable;
import com.example.groupchat_backend.services.TempTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.groupchat_backend.util.Util;

import java.util.List;

@RestController
public class TempTableController {
    @Autowired
    private TempTableService tempTableService;
    @Autowired
    private Util util;

    @GetMapping("/temps")
    private List<TempTable> getTemps(){
        return tempTableService.getTempTable();
    }

    @GetMapping("/getDate")
    private String getDate(){
        return   Util.getCurrentDateTime().toString();
    }
}
