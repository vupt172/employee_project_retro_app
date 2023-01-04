package com.vupt172.controller;

import com.vupt172.service.impl.SyncDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncDataController {
    @Autowired
    SyncDataService service;
    @GetMapping("/sync")
    public Object sync(){
        System.out.println("sync");
      return  service.SyncData();
    }
}
