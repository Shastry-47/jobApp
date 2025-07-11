import { useNavigate } from 'react-router-dom';

const LogoutButton = () => {

  const navigate = useNavigate();

  const handleLogout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('userEmail');
  localStorage.removeItem('userRole');
  navigate('/login');
};

  return (
    <button
      onClick={handleLogout}
      className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 mt-4"
    >
      Logout
    </button>
  );
};

export default LogoutButton;
