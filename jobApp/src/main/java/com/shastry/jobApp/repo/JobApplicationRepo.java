package com.shastry.jobApp.repo;

import com.shastry.jobApp.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepo extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByApplicantId(Long id);
}
