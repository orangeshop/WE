import React from "react";
import couple from "../assets/images/couple.jpg";
import flowerframe from "../assets/images/flowerframe.png";
import back from "../assets/images/back.png";

const InfoTypeSample: React.FC = () => {
  return (
    <div className="font-nanum min-w-[1260px]">
      <div>
        <button>
          <a href="/invitation">
            <img src={back} alt="뒤로가기" className="absolute w-10 mt-8" />
          </a>
        </button>
      </div>
      <div className="flex justify-center">
        <img
          src={flowerframe}
          alt="청첩장 틀"
          className="w-96 h-28 mt-3 mb-10"
        />
      </div>
      <div className="text-center">
        <div className="text-3xl font-semibold letter-space text-[#FEC6A6]">
          WEDDING INVITATION
        </div>
        <div className="text-3xl font-semibold text-[#FFBFBF] mt-8 word-space letter-space">
          <span>김민수</span> <span className="text-[#FE584E]">❤</span>{" "}
          <span>권윤하</span>
        </div>
      </div>
      <div className="flex justify-center">
        <img src={couple} alt="커플" className="w-96 mt-8 mb-10" />
      </div>
    </div>
  );
};

export default InfoTypeSample;
