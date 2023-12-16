package com.example.office.config;

import com.example.office.Entity.employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilder jobBuilder;
    @Autowired
    private StepBuilder stepBuilder;

    public FlatFileItemReader<employee> reader(){
        FlatFileItemReader<employee> Reader = new FlatFileItemReader<>();
        Reader.setResource(new ClassPathResource("records.csv"));
        Reader.setLineMapper(getLineMapper());
        Reader.setLinesToSkip(1);

        return Reader;
    }

    private LineMapper<employee> getLineMapper() {
        DefaultLineMapper<employee> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokinizer = new DelimitedLineTokenizer();
        lineTokinizer.setNames(new String[]{"emplid","ename","email","password","isdeleted"});
        lineTokinizer.setIncludedFields(0,1,2,3,4);



        BeanWrapperFieldSetMapper<employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        fieldSetMapper.setTargetType(employee.class);

        lineMapper.setLineTokenizer(lineTokinizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public employItemProcessor processor(){
        return new employItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<employee>writer(){
        JdbcBatchItemWriter<employee> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<employee>());
        writer.setSql("insert into employee(emplid,ename,email,password,isdeleted) values (:emplid,:ename,:email,:password,:isdeleted)");
        writer.setDataSource(this.dataSource);

        return writer;

    }

    @Bean
    public Job importUserJob(JobRepository jobRepository,Step step1){

        return new JobBuilder("employ_emport-job",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end().build();


    }



    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("Step1",jobRepository)
                .<employee,employee>chunk(10,platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }


}
