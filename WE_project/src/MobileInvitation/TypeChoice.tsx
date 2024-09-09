import React from "react";
import Navbar from "../Components/Navbar";

const TypeChoice: React.FC = () => {
  return (
    <div className="font-nanum">
      <h1>타입 선택</h1>
      <Navbar isScrollSensitive={false} />
    </div>
  );
};

export default TypeChoice;
