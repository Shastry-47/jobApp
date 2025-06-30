import './App.css'
import Landing from './pages/Landing'
import Login from './pages/Login';
import Register from './pages/Register';
import { Route } from 'react-router-dom'
import { Routes } from 'react-router-dom'
import RecruiterDashboard from './pages/RecruiterDashboard';
import ApplicantDashboard from './pages/ApplicantDashboard';
import PostJob from './pages/PostJob';
import JobList from './pages/JobList';
import MyApplications from './pages/MyApplications';
import MyJobPostings from './pages/MyJobPostings';



function App() {

  return (
        <Routes>
          <Route path="/" element={<Landing/>}/>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/recruiter/dashboard" element={<RecruiterDashboard />} />
          <Route path="/recruiter/post-job" element={<PostJob />} />
          <Route path="/applicant/jobs" element={<JobList />} />
          <Route path="/applicant/my-applications" element={<MyApplications />} />
          <Route path="/applicant/dashboard" element={<ApplicantDashboard />} />
          <Route path="/my-jobs" element={<MyJobPostings />} />
        </Routes>
  )
}

export default App
