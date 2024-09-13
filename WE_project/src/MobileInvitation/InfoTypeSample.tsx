import React from "react";
import cardheader from "../assets/images/cardheader.png";

const InfoTypeSample: React.FC = () => {
  return (
    <div>
      <div className="">
        <img src={cardheader} alt="청첩장 헤더" className="w-56" />
      </div>
    </div>
  );
};

export default InfoTypeSample;
