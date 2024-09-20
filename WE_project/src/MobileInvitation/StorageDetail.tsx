import React, { useEffect, useState } from "react";
import {
  getFormalInvitation,
  GetFormalInvitationDto,
} from "../apis/api/getinfotypeinvitation";
import { useParams } from "react-router-dom";

const StorageDetail: React.FC = () => {
  const [invitationData, setInvitationData] =
    useState<GetFormalInvitationDto | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const { invitationId } = useParams<{ invitationId: string }>();

  useEffect(() => {
    const fetchInvitation = async () => {
      try {
        const data = await getFormalInvitation(Number(invitationId));
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

  const groomBirthOrderLabel =
    invitationData.groomBirthOrder === "FIRST"
      ? "장남"
      : invitationData.groomBirthOrder === "SECOND"
      ? "차남"
      : "아들";

  const brideBirthOrderLabel =
    invitationData.brideBirthOrder === "FIRST"
      ? "장녀"
      : invitationData.brideBirthOrder === "SECOND"
      ? "차녀"
      : "딸";

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
        {invitationData.groomMotherFirstName}의 {groomBirthOrderLabel}{" "}
        {invitationData.groomFirstName}
      </p>
      <p>
        {invitationData.brideFatherLastName}
        {invitationData.brideFatherFirstName}{" "}
        {invitationData.brideMotherLastName}
        {invitationData.brideMotherFirstName}의 {brideBirthOrderLabel}{" "}
        {invitationData.brideFirstName}
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

export default StorageDetail;
