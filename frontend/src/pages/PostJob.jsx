import { useState } from 'react';
import axios from '../assets/api/axios';

const PostJob = () => {
  
  const [jobData, setJobData] = useState({
    companyName: '',
    companyDescription: '',
    jobTitle: '',
    jobDescription: '',
    salary: '',
    experience: '',
    location: ''
  });

  const [status, setStatus] = useState('');

  const handleChange = (e) => {
    setJobData({ ...jobData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatus('Posting...');
    try {
      const response = await axios.post('/jobs', jobData);
      setStatus('Job posted successfully!');
      console.log('Response:', response.data);
      setJobData({
        companyName: '',
        companyDescription: '',
        jobTitle: '',
        jobDescription: '',
        salary: '',
        experience: '',
        location: ''
      });
    } catch (err) {
      console.error('Error posting job:', err);
      setStatus('Failed to post job.');
    }
  };

  const fields = [
    { name: 'companyName', label: 'Company Name' },
    { name: 'companyDescription', label: 'Company Description' },
    { name: 'jobTitle', label: 'Job Title' },
    { name: 'jobDescription', label: 'Job Description' },
    { name: 'salary', label: 'Salary' },
    { name: 'experience', label: 'Experience' },
    { name: 'location', label: 'Location' }
  ];

  return (
    <div className="max-w-xl mx-auto mt-10 p-6 bg-white shadow rounded">
      <h2 className="text-2xl font-bold mb-6">Post a Job</h2>
      {status && <p className="mb-4 text-blue-600">{status}</p>}
      <form onSubmit={handleSubmit} className="space-y-4">
        {fields.map(({ name, label }) => (
          <div key={name}>
            <label className="block mb-1">{label}</label>
            <input
              type="text"
              name={name}
              value={jobData[name]}
              onChange={handleChange}
              className="w-full border px-3 py-2 rounded"
              required
            />
          </div>
        ))}
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Post Job
        </button>
      </form>
    </div>
  );
};

export default PostJob;
