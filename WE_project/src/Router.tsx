import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./Main/Home";
import TypeChoice from "./MobileInvitation/TypeChoice";
import Storage from "./MobileInvitation/InfoType/Storage";
import StorageDetail from "./MobileInvitation/InfoType/StorageDetail";
import InfoTypeInvitation from "./MobileInvitation/InfoType/Invitation";
import InvitationEdit from "./MobileInvitation/InfoType/InvitationEdit";
import InfoTypeSample from "./MobileInvitation/InfoType/Sample";
import FreeTypeInvitation from "./MobileInvitation/FreeType/FreeTypeInvitation";
import FreeTypeSample from "./MobileInvitation/FreeType/FreeTypeSample";
import SignUp from "./SignUp/SignUp";
import LogIn from "./LogIn/Login";
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
        <Route
          path="invite/info/edit/:invitationId"
          element={<InvitationEdit />}
        />
        <Route path="/invite/info/sample" element={<InfoTypeSample />} />
        <Route path="/invite/free" element={<FreeTypeInvitation />} />
        <Route path="/invite/free/sample" element={<FreeTypeSample />} />
        <Route path="/account" element={<AccountBook />} />
        {/* 추가적인 라우트들을 여기에 추가하세요@@ */}
      </Routes>
    </Router>
  );
};

export default AppRouter;
