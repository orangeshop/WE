import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./Main/Home";
import TypeChoice from "./MobileInvitation/TypeChoice";
import Storage from "./MobileInvitation/Storage";
import StorageDetail from "./MobileInvitation/StorageDetail";
import InfoTypeInvitation from "./MobileInvitation/InfoTypeInvitation";
import InfoTypeSample from "./MobileInvitation/InfoTypeSample";
import FreeTypeInvitation from "./MobileInvitation/FreeTypeInvitation";
import FreeTypeSample from "./MobileInvitation/FreeTypeSample";
import SignUp from "./SignUp/SignUp";
import LogIn from "./LogIn/Login";
import Mypage from "./MyPage/mypage";
import AccountBook from "./AccountBook/AccountBook";

const AppRouter: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<LogIn />} />
        <Route path="/invitation" element={<TypeChoice />} />
        <Route path="/invitation/storage" element={<Storage />} />
        <Route
          path="/invitation/storage/:invitationId"
          element={<StorageDetail />}
        />
        <Route
          path="/invite/info/:invitationId"
          element={<InfoTypeInvitation />}
        />
        <Route path="/invite/info/sample" element={<InfoTypeSample />} />
        <Route path="/invite/free" element={<FreeTypeInvitation />} />
        <Route path="/invite/free/sample" element={<FreeTypeSample />} />
        <Route path="/mypage" element={<Mypage />} />
        <Route path="/account" element={<AccountBook />} />
        {/* 추가적인 라우트들을 여기에 추가하세요@@ */}
      </Routes>
    </Router>
  );
};

export default AppRouter;
