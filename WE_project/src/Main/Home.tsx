import React, { useEffect, useState  } from "react";
import AOS from "aos";
import "aos/dist/aos.css";
import "./Home.css";
import video_intro from "../assets/images/video_intro.mp4";
import image2 from "../assets/images/image2.png";
import image3 from "../assets/images/image3.jpg";
import image4 from "../assets/images/image4.jpg";

const Home: React.FC = () => {
  const [navbarBackground, setNavbarBackground] = useState(false);

  useEffect(() => {
    AOS.init({ duration: 2500 });

    const handleScroll = () => {
      if (window.scrollY > 50) {
        setNavbarBackground(true);
      } else {
        setNavbarBackground(false);
      }
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <div className="home-container">
      <nav className={`navbar ${navbarBackground ? "navbar-scrolled" : ""}`}>
        <div className="logo">
          <a href="">[ WE : ]</a>
          </div>
        <div className="nav-links">
          <a href="#invitation">Mobile Invitation Card</a>
          <a href="#account">Account Book</a>
          <a href="#signup">Sign Up</a>
          <a href="#login">Login</a>
        </div>
      </nav>

      <div className="video-container">
        <div className="video">
          <video muted autoPlay loop>
            <source src={video_intro} type="video/mp4" />
          </video>
          <div className="overlay-text">
            <div className="main-text">[ WE : ]</div>
            <div className="sub-text">
              " 우리
              <span className="sub-color">[WE]</span>가 함께 준비하는 웨딩
              <span className="sub-color">[WEdding] </span>"
            </div>
          </div>
        </div>
      </div>

      <div className="sections">
        <div className="section" data-aos="fade-up">
          <img src={image2} alt="QR 인식 송금" className="section-image" />
          <p>Ep 01</p>
          <p>QR 인식으로 보다 편리한 축의금 송금</p>
        </div>
        <div className="section" data-aos="fade-up">
          <img src={image3} alt="모바일 청첩장" className="section-image" />
          <p>Ep 02</p>
          <p>직접 커스텀해서 신랑, 신부가 함께 만드는 모바일 청첩장</p>
        </div>
        <div className="section" data-aos="fade-up">
          <img
            src={image4}
            alt="결혼준비 체크리스트"
            className="section-image"
          />
          <p>Ep 03</p>
          <p>어려운 결혼준비 체크리스트, 캘린더로 손쉬운 일정 관리</p>
        </div>
      </div>

      <footer className="footer">
        <p>[WE: ] 구미시 삼성전자 어쩌구저쩌구 304호</p>
        <p>Email: wei@ssafy.com</p>
      </footer>
    </div>
  );
};

export default Home;
