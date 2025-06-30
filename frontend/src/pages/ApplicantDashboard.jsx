import LogoutButton from "../components/LogoutButton";
import { Link } from "react-router-dom";

const ApplicantDashboard = () => {

  return (
    <div className="p-10">
      <h1 className="text-3xl font-bold">Applicant Dashboard</h1>
      <p className="mt-4">Welcome! Start applying to jobs now.</p>

      <LogoutButton/>
      
      <Link to="/applicant/jobs" className="text-blue-600 underline mt-4 block">
        🔍 Browse Jobs
      </Link>

      <Link to="/applicant/my-applications" className="text-blue-600 underline block mt-4">
        📁 View My Applications
      </Link>
    </div>
  );
};

export default ApplicantDashboard;
