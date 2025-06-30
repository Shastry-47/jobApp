import { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import axios from '../assets/api/axios';


const Login = () => {

  const [searchParams] = useSearchParams();
  const role = searchParams.get('role');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();

  useEffect(() => {
    if (!role) {
      window.location.href = '/';
    }
  }, [role]);

  const handleLogin = async (e) => {
  e.preventDefault();

  try {

    const response = axios.post('/login',{
        email,
        password,
        role: role?.toUpperCase()
    });

    if (!response.ok) {
      const errorData = await response.json();
      alert('Login failed: ' + (errorData.message || 'Unknown error'));
      return;
    }

    const data = (await response).data;

    localStorage.setItem('token', data.token);
    localStorage.setItem('userRole', data.role);
    localStorage.setItem('userName', data.name);
    localStorage.setItem('userEmail', email);

    if (role === 'recruiter') {
      navigate('/recruiter/dashboard');
    } else {
      navigate('/applicant/dashboard');
    }
  } catch (error) {
    alert('Error logging in: ' + error.message);
  }
};

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <h2 className="text-2xl font-bold mb-6">
        {role === 'recruiter' ? 'Recruiter' : 'Applicant'} Login
      </h2>
      <form
        onSubmit={handleLogin}
        className="bg-white p-6 rounded shadow-md w-80"
      >
        <div className="mb-4">
          <label className="block mb-1">Email</label>
          <input
            type="email"
            className="w-full border px-3 py-2 rounded"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="mb-4">
          <label className="block mb-1">Password</label>
          <input
            type="password"
            className="w-full border px-3 py-2 rounded"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded w-full hover:bg-blue-700"
        >
          Login
        </button>
      </form>
      <p className="text-sm mt-4 text-center">
        Don’t have an account?{' '}
        <Link to={`/register?role=${role}`} className="text-blue-600 underline">
            Register here
        </Link>
        </p>
    </div>
  );
};

export default Login;
