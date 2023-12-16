package com.example.office.service.impl;

import com.example.office.DTO.employdto;
import com.example.office.Entity.employee;
import com.example.office.password.generator;
import com.example.office.repo.employrepo;
import com.example.office.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class EmployImpl implements EmployeeService {
    @Autowired
    private employrepo ERepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private generator passwordgenerator;

    @Override
    public String addEmployee(employdto employDto) {

        employee Stored=ERepo.findByEmail(employDto.getEmail());
        if (Stored==null){
            if(employDto.getPassword()==null){
                String st=generator.generatePass();
                employDto.setPassword(st);
            }
            employee e=new employee(
                    employDto.getEmplid(),
                    employDto.getEname(),
                    employDto.getEmail(),
                    this.passwordEncoder.encode(employDto.getPassword()),"false");
            ERepo.save(e);
            return e.getEname();
        }
        return "Email already is in use try with different email ";

    }



    @Override
    public Void deletebyid(int id) {
        ERepo.deleteById(id);

        return null;
    }

    @Override
    public String getById(int id) {
        Optional<employee> byId = ERepo.findById(id);
        return byId.toString();
    }


}
