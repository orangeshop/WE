import React, { useEffect, useState } from "react";
import Navbar from "../Components/Navbar";
import {
  getCoupleInvitation,
  GetCoupleInvitationDto,
} from "../apis/api/coupleinvitation";

const Mypage: React.FC = () => {
  const [coupleId, setCoupleId] = useState<number | null>(null);
  const accessToken = localStorage.getItem("accessToken");
  const email = localStorage.getItem("email");

  useEffect(() => {
    const fetchCoupleInvitations = async () => {
      try {
        if (!accessToken) {
          throw new Error("Access token not found");
        }
        const invitations: GetCoupleInvitationDto[] = await getCoupleInvitation(
          accessToken
        );
        if (invitations.length > 0) {
          setCoupleId(invitations[0].coupleId);
        }
      } catch (error) {
        console.error("Error fetching couple invitations:", error);
      }
    };
    fetchCoupleInvitations();
  }, [accessToken]);

  return (
    <div className="font-nanum">
      <Navbar isScrollSensitive={false} />
      <div>
        {coupleId !== null ? (
          <p>Couple ID: {coupleId}</p>
        ) : (
          <p>Couple ID를 찾을 수 없습니다.</p>
        )}
        {email ? <p>이메일: {email}</p> : <p>이메일 정보가 없습니다.</p>}
      </div>
    </div>
  );
};

export default Mypage;
