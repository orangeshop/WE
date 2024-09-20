import React, { useEffect, useState } from "react";
import {
  getFormalInvitation,
  GetFormalInvitationDto,
} from "../apis/api/getinfotypeinvitation";

const Storage: React.FC = () => {
  const [invitationData, setInvitationData] =
    useState<GetFormalInvitationDto | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchInvitation = async () => {
      try {
        const data = await getFormalInvitation(4); // 추후에 실제 id로 수정
        setInvitationData(data);
        setLoading(false);
      } catch (error) {
        console.error("정보형 청첩장 조회 중 오류 발생:", error);
        throw error;
      }
    };

    fetchInvitation();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!invitationData) {
    return <div>No invitation data available</div>;
  }

  return (
    <div>
      <h1>청첩장 보관함</h1>
      <p>
        {invitationData.groomLastName}
        {invitationData.groomFirstName} {invitationData.brideLastName}
        {invitationData.brideFirstName}
      </p>
      <img src={invitationData.url} alt="대표 사진" />
      <p>
        {invitationData.groomFatherLastName}
        {invitationData.groomFatherFirstName}{" "}
        {invitationData.groomMotherLastName}
        {invitationData.groomMotherFirstName}의 {invitationData.groomBirthOrder}
        {invitationData.brideFatherLastName}
      </p>
      <p>{invitationData.date}</p>
      <p>
        {invitationData.timezone === "AM" ? "오전" : "오후"}{" "}
        {invitationData.hour}시 {invitationData.minute}분
      </p>
      <p>
        {invitationData.weddingHall}, {invitationData.addressDetail}
      </p>
      <p>{invitationData.address}</p>
      <p>인사말: {invitationData.greetings}</p>
    </div>
  );
};

export default Storage;
