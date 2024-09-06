import React, { useEffect, useState } from "react";
import AOS from "aos";
import "aos/dist/aos.css";
import video_intro from "../assets/images/video_intro.mp4";
import happymoney from "../assets/images/happymoney.png";
import invitation from "../assets/images/invitation.jpg";
import calendar from "../assets/images/calendar.jpg";
// import mainlogo from "../assets/images/mainlogo.png";
// import we from "../assets/images/WE.jpg";

const Home: React.FC = () => {
  const [navbarBackground, setNavbarBackground] = useState(false);

  useEffect(() => {
    AOS.init({ duration: 2000 });

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
    <div className="font-nanum box-border">
      {/* Navbar */}
      <nav
        className={`fixed top-0 left-0 w-full flex items-center py-5 px-3 z-50 transition-colors duration-300 ease-in-out border-b ${
          navbarBackground
            ? "bg-white border-gray-300"
            : "bg-transparent border-white/30"
        }`}
      >
        <div className="mr-24 ml-12 text-2xl">
          <a
            href=""
            className={`${navbarBackground ? "text-black" : "text-white"}`}
          >
            [ WE : ]
          </a>
        </div>
        <div className="flex gap-12 justify-center mt-1">
          <a
            href="#invitation"
            className={`${
              navbarBackground ? "text-black" : "text-white"
            } text-lg`}
          >
            Mobile Invitation Card
          </a>
          <a
            href="#account"
            className={`${
              navbarBackground ? "text-black" : "text-white"
            } text-lg`}
          >
            Account Book
          </a>
          <a
            href="signup"
            className={`${
              navbarBackground ? "text-black" : "text-white"
            } text-lg`}
          >
            Sign Up
          </a>
          <a
            href="#login"
            className={`${
              navbarBackground ? "text-black" : "text-white"
            } text-lg`}
          >
            Login
          </a>
        </div>
      </nav>

      {/* Video Section */}
      <div className="relative w-full overflow-hidden">
        <video
          muted
          autoPlay
          loop
          className="opacity-70 w-full h-auto object-cover"
        >
          <source src={video_intro} type="video/mp4" />
        </video>
        {/* <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center text-white drop-shadow-md">
          <div className="text-[#e5d8b5] text-5xl mb-4">[ WE : ]</div>
          <div className="text-xl">
            " 우리
            <span className="text-[#e5d8b5]">[WE]</span>가 함께 준비하는 웨딩
            <span className="text-[#e5d8b5]">[WEdding]</span> "
          </div>
        </div> */}
      </div>

      {/* Introduction Section */}
      <div className="flex flex-col mt-60 items-center text-center">
        {/* <img src={mainlogo} alt="mainlogo" className="w-80 mb-10" /> */}
        {/* <img src={we} alt="we" className="w-60 mb-20" /> */}
        <h2 className="text-5xl mb-8">[ WE : ]</h2>
        <div className="mb-60 text-lg">
          <p>결혼이라는 새로운 출발을 앞둔 '우리(WE)'와,</p>
          <p>함께 준비하는 '웨딩(WEdding)'의 의미를 지녔습니다.</p>
          <p>결혼 자금과 축의금 장부를 효과적으로 관리하고,</p>
          <p>
            간편한 결혼 준비 체크리스트 확인과 직접 만드는 모바일 청첩장까지,
          </p>
          <p>
            결혼 준비의 시작부터 끝까지 저희 [ WE : ]는 여러분과 함께 합니다.
          </p>
        </div>
      </div>

      {/* Sections */}
      <div className="mb-60 mx-10 border border-gray-100"></div>
      <div className="items-center text-center text-4xl mb-20">Services</div>

      <div className="flex justify-around gap-10 mt-20 mb-10 mx-10">
        <div className="flex flex-col" data-aos="fade-up">
          <img src={happymoney} alt="QR 인식 송금" className="mb-3 h-80" />
          <div className="mx-2">
            <p className="text-lg text-gray-400">Ep 01</p>
            <p className="text-xl font-bold mb-4">축의금 관리</p>
            <p className="text-md">
              QR 인식으로 보다 편리한 축의금 송금과 장부 관리
            </p>
          </div>
        </div>
        <div className="flex flex-col" data-aos="fade-up">
          <img src={invitation} alt="모바일 청첩장" className="mb-3 h-80" />
          <div className="mx-2">
            <p className="text-lg text-gray-400">Ep 02</p>
            <p className="text-xl font-bold mb-4">모바일 청첩장 제작</p>
            <p>필요한 정보만 입력한 만들어지는 정보형 모바일 청첩장,</p>
            <p>직접 커스텀해서 신랑 신부가 함께 만드는 자유형 모바일 청첩장</p>
          </div>
        </div>
        <div className="flex flex-col" data-aos="fade-up">
          <img src={calendar} alt="결혼준비 체크리스트" className="mb-3 h-80" />
          <div className="mx-2">
            <p className="text-lg text-gray-400">Ep 03</p>
            <p className="text-xl font-bold mb-4">일정 관리</p>
            <p>복잡한 결혼준비 체크리스트, 캘린더로 손쉬운 일정 관리</p>
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="mt-20 text-sm text-gray-600 py-4 text-center bg-gray-100">
        <p>ⓒ WE 웨딩</p>
        <p>삼성 청년 SW 아카데미 D104팀</p>
        <p>Email: wewedding@ssafy.com</p>
      </footer>
    </div>
  );
};

export default Home;
