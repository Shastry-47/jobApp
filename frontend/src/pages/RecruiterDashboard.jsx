import LogoutButton from "../components/LogoutButton";
import { Link } from 'react-router-dom';

const RecruiterDashboard = () => {
  return (
    <div className="p-10">
      <h1 className="text-3xl font-bold">Recruiter Dashboard</h1>
      <p className="mt-4">Welcome! You can now post and manage jobs.</p>

      <LogoutButton />

      <Link to="/recruiter/post-job" className="text-blue-600 underline mt-4 block">
        + Post a New Job
      </Link>

      <Link to="/my-jobs" className="text-green-600 underline mt-2 block">
        📄 View Posted Jobs
      </Link>
    </div>
  );
};

export default RecruiterDashboard;
