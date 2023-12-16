package com.example.office.config;

import com.example.office.Entity.employee;
import org.springframework.batch.item.ItemProcessor;

public class employItemProcessor implements ItemProcessor<employee,employee> {
    @Override
    public employee process(employee employee) throws Exception {
        return employee;
    }
}
