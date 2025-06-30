import Header from '../components/Header';
import Footer from '../components/Footer';
import SplitLogin from '../components/SplitLogin';

const Landing = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <Header />
      <SplitLogin />
      <Footer />
    </div>
  );
};

export default Landing;
