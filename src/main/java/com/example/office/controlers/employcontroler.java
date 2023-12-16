package com.example.office.controlers;

import com.example.office.DTO.employdto;
import com.example.office.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/employee")
public class employcontroler {

    @Autowired
    EmployeeService EService;
    @PostMapping("/save")
    public String SaveEmploy(@RequestBody employdto EmployDto){
        String s=EService.addEmployee(EmployDto);
        return s;
    }

    @GetMapping("/user/{id}")
    public String getbyid(@PathVariable("id") int id){
        String byId = EService.getById(id);
        return byId;

    }

    @GetMapping("/deluser/{id}")
    public Void deletebyid(@PathVariable("id") int  id){

        Void deletebyid = EService.deletebyid(id);
        return deletebyid;
    }

}
