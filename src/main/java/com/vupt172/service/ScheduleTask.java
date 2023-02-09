package com.vupt172.service;

import com.vupt172.dto.SyncDataResult;
import com.vupt172.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev","prod"})

public class ScheduleTask {
    @Autowired
    SyncDataService service;
    @Scheduled(cron = "0 54 16 * * TUE")
    @Async
    public void AutoSyncDataEveryWeek() {
        System.out.println("SYNC RESULT");
        SyncDataResult syncDataResult= service.SyncData();
        System.out.println("Not Active Employee");
        syncDataResult.getNotActiveEmployeeList().forEach(Employee::showEmailInfo);
        System.out.println("New Employee");
        syncDataResult.getNewEmployeeList().forEach(Employee::showEmailInfo);
        System.out.println("END");
    }
}
