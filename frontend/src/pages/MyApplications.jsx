import { useState, useEffect } from 'react';
import axios from '../assets/api/axios';

const MyApplications = () => {
  const [applications, setApplications] = useState([]);
  const [status, setStatus] = useState('Loading...');

  useEffect(() => {
    const fetchApps = async () => {
      try {
        const res = await axios.get('/applications/applicant');
        setApplications(res.data);
        setStatus('');
      } catch (err) {
        console.error('Error fetching applications', err);
        setStatus('Failed to load applications.');
      }
    };

    fetchApps();
  }, []);

  return (
    <div className="p-6">
      <h2 className="text-3xl font-bold mb-6">My Applications</h2>

      {status && <p>{status}</p>}

      {!status && applications.length === 0 ? (
        <p>You have not applied to any jobs yet.</p>
      ) : (
        <div className="space-y-4">
          {applications.map((app) => (
            <div key={app.id} className="border p-4 rounded shadow">
              <h3 className="text-xl font-semibold">{app.title}</h3>
              <p>{app.company}</p>
              <p>
                Resume:{' '}
                <a
                  href={app.resumeURL}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-blue-600 underline"
                >
                  View
                </a>
              </p>
              <p>
                Status:{' '}
                <span
                  className={`font-medium ${
                    app.status === 'Accepted'
                      ? 'text-green-600'
                      : app.status === 'Rejected'
                      ? 'text-red-600'
                      : 'text-gray-800'
                  }`}
                >
                  {app.status}
                </span>
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default MyApplications;
