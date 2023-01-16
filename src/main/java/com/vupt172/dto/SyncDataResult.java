package com.vupt172.dto;

import com.vupt172.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
@Data
@AllArgsConstructor
public class SyncDataResult {
    List<Employee> notActiveEmployeeList;
    List<Employee> newEmployeeList;
}
