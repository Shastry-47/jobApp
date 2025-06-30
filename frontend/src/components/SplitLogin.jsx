import { useNavigate } from 'react-router-dom';

const SplitLogin = () => {
  
  const navigate = useNavigate();

  return (
    <div className="flex flex-1 h-full">
      {/* Applicant Login */}
      <div
        className="w-1/2 flex flex-col items-center justify-center bg-gray-100 hover:bg-gray-200 cursor-pointer"
        onClick={() => navigate('/login?role=applicant')}
      >
        <h2 className="text-2xl font-bold mb-4">Applicant</h2>
        <p>Login to search and apply for jobs</p>
      </div>

      {/* Recruiter Login */}
      <div
        className="w-1/2 flex flex-col items-center justify-center bg-gray-300 hover:bg-gray-400 cursor-pointer"
        onClick={() => navigate('/login?role=recruiter')}
      >
        <h2 className="text-2xl font-bold mb-4">Recruiter</h2>
        <p>Login to post and manage jobs</p>
      </div>
    </div>
  );
};

export default SplitLogin;
