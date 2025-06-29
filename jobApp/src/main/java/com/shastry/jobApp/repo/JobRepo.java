package com.shastry.jobApp.repo;

import com.shastry.jobApp.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job,Long> {

        @Query("SELECT j FROM Job j WHERE " +
                "(:title IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
                "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
                "(:companyName IS NULL OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))) AND " +
                "(:minSalary IS NULL OR j.salary >= :minSalary) AND " +
                "(:minExperience IS NULL OR j.experience >= :minExperience)")
        Page<Job> searchJobs(@Param("title") String title,
                             @Param("location") String location,
                             @Param("companyName") String companyName,
                             @Param("minSalary") Double minSalary,
                             @Param("minExperience") Integer minExperience,
                             Pageable pageable );

        List<Job> findByRecruiterId(Long recruiterId);
}
