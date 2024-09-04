import React from "react";
import './Home.css';
import video_intro from '../assets/images/video_intro.mp4'
import image2 from '../assets/images/image2.png'
import image3 from '../assets/images/image3.jpg'
import image4 from '../assets/images/image4.jpg'

const Home: React.FC = () => {
  return (
    <div className="home-container">
      <div className="video-container">
        <div className="video">
          <video muted autoPlay loop>
          <source src={video_intro} type="video/mp4" />        
        </video>
        <div className="overlay-text">
          <div className = "main_text">[WE: ]</div>
          <h2>'우리(WE)'가 함께 준비하는 '웨딩(WEdding)'</h2>
        </div>
        </div>
      </div>
        <h1>[WE : ]</h1>
        <h5>Financial Plan for Wedding</h5>
      
      <div className="sections">
        <div className="section">
          <img src={image2} alt="QR 인식 송금" className="section-image" />
          <p>QR 인식으로 보다 편리한 축의금 송금</p>
        </div>
        <div className="section">
          <img src={image3} alt="모바일 청첩장" className="section-image" />
          <p>직접 커스텀해서 신랑, 신부가 함께 만드는 모바일 청첩장</p>
        </div>
        <div className="section">
          <img src={image4} alt="결혼준비 체크리스트" className="section-image" />
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
