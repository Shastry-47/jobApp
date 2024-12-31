package com.shastry.jobApp.service;

import com.shastry.jobApp.exceptions.jobNotFoundException;
import com.shastry.jobApp.model.Job;
import com.shastry.jobApp.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;

    // Retrieve all jobs from the database
    public List<Job> getAllJobs() {
        List<Job> jobs=repo.findAll();
        System.out.println(jobs);
        return jobs;
    }

    public Job createJob(Job job) {
        return repo.save(job);
    }

    public Job getJob(int jobId) {
        Optional<Job> job = repo.findById(jobId);
        return job.orElseThrow(() -> new jobNotFoundException("Job not found with id: " + jobId));
    }

    public Job updateJob(Job job, int jobId) {
        Job newJob = repo.findById(jobId).orElseThrow(()->new jobNotFoundException("job not exist with id: " + jobId));

        newJob.setJobDescription(job.getJobDescription());
        newJob.setJobTitle(job.getJobTitle());
        newJob.setExperience(job.getExperience());
        newJob.setSalary(job.getSalary());
        newJob.setLocation(job.getLocation());
        newJob.setCompanyDescription(job.getCompanyDescription());
        newJob.setCompanyName(job.getCompanyName());

        Job updatedJob = repo.save(newJob);
        return updatedJob;

    }

    public void deleteJob(int jobId) {

        Job job = repo.findById(jobId).orElseThrow(()->new jobNotFoundException("no job found with id: "+jobId));
        repo.deleteById(jobId);

    }
}