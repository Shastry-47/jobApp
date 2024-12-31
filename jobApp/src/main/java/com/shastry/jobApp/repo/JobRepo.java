package com.shastry.jobApp.repo;

import com.shastry.jobApp.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends JpaRepository<Job,Integer> {

}
