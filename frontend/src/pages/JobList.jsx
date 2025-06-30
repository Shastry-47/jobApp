import { useState, useEffect } from 'react';
import axios from '../assets/api/axios';

const JobList = () => {
  const [query, setQuery] = useState('');
  const [jobs, setJobs] = useState([]);
  const [selectedJobId, setSelectedJobId] = useState(null);
  const [resumeURL, setResumeURL] = useState('');

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        const response = await axios.get('/jobs');
        setJobs(response.data);
        console.log('jobs:', response.data);
      } catch (error) {
        console.error('Failed to fetch jobs:', error);
      }
    };
    fetchJobs();
  }, []);

  const filteredJobs = jobs.filter(
    (job) =>
      job.jobTitle.toLowerCase().includes(query.toLowerCase()) ||
      job.companyName.toLowerCase().includes(query.toLowerCase()) ||
      job.location.toLowerCase().includes(query.toLowerCase())
  );

  const handleApply = (jobId) => {
    console.log('Selected Job ID:', jobId);
    setSelectedJobId(jobId);
  };

  const handleSubmitApplication = async (e) => {
    e.preventDefault();
    const job = jobs.find((j) => j.jobId === selectedJobId || j.id === selectedJobId);
    console.log('Applying to job:', job);

    if (job && resumeURL.trim()) {
      try {
        await axios.post('/applications', {
          jobId: job.jobId || job.id,
          resumeURL,
        });

        alert('Applied successfully!');
        setSelectedJobId(null);
        setResumeURL('');
      } catch (err) {
        console.error('Error applying:', err);
        alert('Failed to apply.');
      }
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-3xl font-bold mb-6">Job Listings</h2>

      <input
        type="text"
        placeholder="Search by title, company, or location"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        className="w-full mb-6 p-3 border rounded"
      />

      {filteredJobs.length === 0 ? (
        <p>No jobs found.</p>
      ) : (
        <div className="space-y-4">
          {filteredJobs.map((job) => (
            <div
              key={job.jobId}
              className="border rounded p-4 shadow hover:shadow-lg transition"
            >
              <h3 className="text-xl font-semibold">{job.jobTitle}</h3>
              <p>
                {job.companyName} • {job.location}
              </p>
              <p>
                💰 {job.salary} | 🧰 {job.experience} years
              </p>
              <p>{job.jobDescription}</p>

              {selectedJobId === job.jobId ? (
                <form onSubmit={handleSubmitApplication} className="mt-4 space-y-2">
                  <input
                    type="url"
                    placeholder="Enter Resume URL"
                    value={resumeURL}
                    onChange={(e) => setResumeURL(e.target.value)}
                    required
                    className="w-full p-2 border rounded"
                  />
                  <button
                    type="submit"
                    className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                  >
                    Submit Application
                  </button>
                </form>
              ) : (
                <button
                  onClick={() => handleApply(job.jobId)}
                  className="mt-2 px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                >
                  Apply
                </button>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default JobList;
