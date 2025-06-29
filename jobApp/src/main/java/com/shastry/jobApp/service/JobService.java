package com.shastry.jobApp.service;

import com.shastry.jobApp.model.Job;
import com.shastry.jobApp.repo.JobRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepo jobRepo;

    public Job save(Job job)
    {
        System.out.println("this is posting job service");
        return jobRepo.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepo.findAll();
    }

    public List<Job> getJobsByRecruiter(Long recruiterId) {
        return jobRepo.findByRecruiterId(recruiterId);
    }

    public Optional<Object> getJobById(Long aLong) {
        return Optional.empty();
    }

    public Optional<Job> findById(Long jobId) {
        return jobRepo.findById(jobId);
    }

    public Page<Job> searchJobs(String title, String location, String companyName, Double minSalary, Integer minExperience, Pageable pageable) {
        return jobRepo.searchJobs(title, location, companyName, minSalary, minExperience , pageable);
    }

}