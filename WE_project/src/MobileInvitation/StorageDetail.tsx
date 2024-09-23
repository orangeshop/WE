import React, { useEffect, useState } from "react";
import {
  getFormalInvitation,
  GetFormalInvitationDto,
} from "../apis/api/getinfotypeinvitation";
import { useParams } from "react-router-dom";
// import back from "../assets/images/back.png";
// import flowerframe from "../assets/images/flowerframe.png";
import flower_sm from "../assets/images/flower-sm.png";
// import paper from "../assets/images/paper.avif";
import pinkpaper from "../assets/images/pinkpaper.jpeg";

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
  });

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
    <div className="relative font-nanum min-w-[1500px] w-full">
      <div
        className="absolute inset-0 bg-cover bg-center"
        style={{
          backgroundImage: `url(${pinkpaper})`,
          opacity: 0.2,
        }}
      />
      <div className="relative z-10">
        <div className="text-center w-full">
          <div>
            <button className="flex justify-start ml-20 absolute mt-10 bg-transparent">
              <a href="/invitation">
                <p className="text-[#C1A56C]">{"<"} 뒤로가기</p>
              </a>
            </button>

            <div className="text-lg font-semibold letter-space pt-20 text-center text-[#800000]">
              WEDDING INVITATION
            </div>
          </div>
          <div className="letter-space">
            <p className="text-2xl mt-10">
              {invitationData.groomLastName}
              {invitationData.groomFirstName}{" "}
              <span className="text-[#FE584E]">❤</span>{" "}
              {invitationData.brideLastName}
              {invitationData.brideFirstName}
            </p>
          </div>
          <div className="w-full flex justify-center mt-10">
            <img
              src={invitationData.url}
              alt="대표 사진"
              className="w-1/3 h-auto"
            />
          </div>
          <div className="text-xl">
            <p className="mt-10">
              {invitationData.date}{" "}
              {invitationData.timezone === "AM" ? "오전" : "오후"}{" "}
              {invitationData.hour}시 {invitationData.minute}분
            </p>
            <p>
              {invitationData.weddingHall}, {invitationData.addressDetail}
            </p>
            <div className="flex justify-center mt-20">
              <div className="w-[480px] h-[620px] bg-[#FFFAF0] mb-20 justify-center">
                <div className="w-full flex justify-center">
                  <img
                    src={flower_sm}
                    alt="꽃"
                    className="justify-center w-10 mt-5 mb-5"
                  />
                </div>
                <p className="text-[#B3B3B3] letter-space-sm mb-5">
                  INVITATION
                </p>
                <div className="border border-gray-200 mx-10 mb-10"></div>
                <p className="text-[16px] mx-24 mb-10">
                  {invitationData.greetings.split("\n").map((line, index) => (
                    <React.Fragment key={index}>
                      {line}
                      <br />
                    </React.Fragment>
                  ))}
                </p>

                <div className="border border-gray-200 mx-10 mb-10"></div>
                <div className="text-[16px]">
                  <p>
                    <span className="font-semibold">
                      {invitationData.groomFatherLastName}
                      {invitationData.groomFatherFirstName}
                      {"ㆍ"}
                      {invitationData.groomMotherLastName}
                      {invitationData.groomMotherFirstName}
                    </span>
                    <span className="text-[#A88E97]">
                      의 {groomBirthOrderLabel}{" "}
                    </span>
                    <span className="font-semibold">
                      {invitationData.groomFirstName}
                    </span>
                  </p>
                  <p>
                    <span className="font-semibold">
                      {invitationData.brideFatherLastName}
                      {invitationData.brideFatherFirstName}
                      {"ㆍ"}
                      {invitationData.brideMotherLastName}
                      {invitationData.brideMotherFirstName}
                    </span>
                    <span className="text-[#A88E97]">
                      의 {brideBirthOrderLabel}{" "}
                    </span>{" "}
                    <span className="font-semibold">
                      {invitationData.brideFirstName}
                    </span>
                  </p>
                </div>
              </div>
            </div>

            <p>{invitationData.address}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StorageDetail;
