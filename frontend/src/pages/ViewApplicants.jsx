// src/pages/ViewApplicants.jsx
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from '../assets/api/axios';

const ViewApplicants = () => {
  const { jobId } = useParams();
  const [applicants, setApplicants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchApplicants = async () => {
      try {
        const response = await axios.get(`/applications/job/${jobId}`);
        setApplicants(response.data);
      } catch (err) {
        console.error('Failed to fetch applicants:', err);
        setError('Could not load applicants');
      } finally {
        setLoading(false);
      }
    };

    fetchApplicants();
  }, [jobId]);

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Applicants for Job #{jobId}</h2>

      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p className="text-red-600">{error}</p>
      ) : applicants.length === 0 ? (
        <p>No applicants for this job yet.</p>
      ) : (
        <div className="space-y-4">
          {applicants.map((applicant) => (
            <div key={applicant.id} className="border p-4 rounded shadow bg-white">
              <p><strong>Name:</strong> {applicant.applicantName}</p>
              <p><strong>Email:</strong> {applicant.applicantEmail}</p>
              <p><strong>Status:</strong> {applicant.status}</p>
              <p>
                <strong>Resume:</strong>{' '}
                <a
                  href={applicant.resumeUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-blue-600 underline"
                >
                  View Resume
                </a>
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ViewApplicants;
