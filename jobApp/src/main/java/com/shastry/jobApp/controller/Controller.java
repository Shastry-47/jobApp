package com.shastry.jobApp.controller;

import com.shastry.jobApp.model.Job;
import com.shastry.jobApp.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") // Replace with your frontend URL
@RestController
public class Controller {

    @Autowired
    private JobService service;

    @GetMapping("/")
    public String greet() {
        return "Hello Erripuka";
    }

    @GetMapping("/jobs")
    @ResponseBody
    public List<Job> getAllJobs() {
        return service.getAllJobs();
    }

    @PostMapping("/")
    public ResponseEntity<Job> createJob(@Valid @RequestBody Job job)
    {
        Job newJob = service.createJob(job);
        return new ResponseEntity<>(newJob,HttpStatus.CREATED);
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Job> getJob(@PathVariable int jobId)
    {
        Job job = service.getJob(jobId);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<Job> updateJob(@RequestBody Job job,@PathVariable int jobId)
    {
        Job updatedJob = service.updateJob(job,jobId);
        return new ResponseEntity<>(updatedJob,HttpStatus.CREATED);
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable int jobId)
    {
        service.deleteJob(jobId);
        return ResponseEntity.ok("Job deleted successfully");
    }

}
