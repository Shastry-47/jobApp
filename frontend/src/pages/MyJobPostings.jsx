import { useEffect, useState } from 'react';
import axios from '../assets/api/axios';

const MyJobPostings = () => {
  
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [applicants, setApplicants] = useState({});
  const [selectedJobId, setSelectedJobId] = useState(null);

  useEffect(() => {
    const fetchMyJobs = async () => {
      try {
        const response = await axios.get('/jobs/recruiter');
        setJobs(response.data);
      } catch (err) {
        setError('Failed to fetch your job postings.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchMyJobs();
  }, []);

  const viewApplicants = async (jobId) => {
    try {
      const response = await axios.get(`/applications/job/${jobId}`);
      setApplicants((prev) => ({ ...prev, [jobId]: response.data }));
      setSelectedJobId(jobId);
    } catch (err) {
      console.error('Error fetching applicants:', err);
      alert('Failed to load applicants for this job.');
    }
  };

  return (
    <div className="p-6 max-w-4xl mx-auto">
      <h2 className="text-3xl font-bold mb-6">My Job Postings</h2>

      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p className="text-red-600">{error}</p>
      ) : jobs.length === 0 ? (
        <p>You have not posted any jobs yet.</p>
      ) : (
        <div className="space-y-6">
          {jobs.map((job) => (
            <div key={job.jobId} className="border rounded p-4 shadow">
              <h3 className="text-xl font-semibold">{job.jobTitle}</h3>
              <p>{job.companyName} • {job.location}</p>
              <p>💰 {job.salary} | 🧰 {job.experience} years</p>
              <p>{job.jobDescription}</p>

                <Link
                    to={`/recruiter/job/${job.jobId}/applicants`}
                    className="mt-3 inline-block px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                    >
                    View Applicants
                </Link>

              {selectedJobId === job.jobId && applicants[job.jobId] && (
                <div className="mt-4 bg-gray-100 p-3 rounded">
                  <h4 className="text-lg font-bold mb-2">Applicants:</h4>
                  {applicants[job.jobId].length === 0 ? (
                    <p>No applicants yet.</p>
                  ) : (
                    <ul className="space-y-2">
                      {applicants[job.jobId].map((app) => (
                        <li key={app.id} className="border p-2 rounded bg-white shadow-sm">
                          <p><strong>Name:</strong> {app.applicantName}</p>
                          <p><strong>Email:</strong> {app.applicantEmail}</p>
                          <p><strong>Status:</strong> {app.status}</p>
                          <p>
                            <strong>Resume:</strong>{' '}
                            <a
                              href={app.resumeUrl}
                              target="_blank"
                              rel="noopener noreferrer"
                              className="text-blue-600 underline"
                            >
                              View Resume
                            </a>
                          </p>
                        </li>
                      ))}
                    </ul>
                  )}
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default MyJobPostings;
