import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./Main/Home";
import InfoTypeInvitation from "./MobileInvitation/InfoTypeInvitation";
import FreeTypeInvitation from "./MobileInvitation/FreeTypeInvitation";
import SignUp from "./SignUp/SignUp";
import LogIn from "./LogIn/Login";

const AppRouter: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<LogIn />} />
        <Route path="/invite/info" element={<InfoTypeInvitation />} />
        <Route path="/invite/free" element={<FreeTypeInvitation />} />
        {/* 추가적인 라우트들을 여기에 추가하세요@@ */}
      </Routes>
    </Router>
  );
};

export default AppRouter;
