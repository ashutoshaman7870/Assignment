package com.example.office.service;

import com.example.office.DTO.employdto;
import com.example.office.DTO.loginDTO;
import com.example.office.Entity.employee;
import com.example.office.payload.loginresponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {


    String addEmployee(employdto employDto);



    Void deletebyid(int id);
    String getById(int id);
}
