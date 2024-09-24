import React, { useEffect, useState, useCallback } from "react";
import {
  getFormalInvitation,
  GetFormalInvitationDto,
} from "../../apis/api/getinfotypeinvitation";
import { useParams, useNavigate } from "react-router-dom";
import flower_sm from "../../assets/images/flower-sm.png";
import leaf from "../../assets/images/leaf.png";
import rose from "../../assets/images/rose.png";
import pinkpaper from "../../assets/images/pinkpaper.jpeg";
import kakaoicon from "../../assets/images/kakaoicon.png";
import copyicon from "../../assets/images/copyicon.png";
import InvitationMap from "./InvitationMap";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "./StorageDetail.css";
import dayjs from "dayjs";
import { deleteFormalInvitation } from "../../apis/api/deleteinfotypeinvitation";
import {
  getAccountInfo,
  GetAccountInfoDto,
} from "../../apis/api/getaccountinfo";

const StorageDetail: React.FC = () => {
  const [invitationData, setInvitationData] =
    useState<GetFormalInvitationDto | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const { invitationId } = useParams<{ invitationId: string }>();
  const [accountNo, setAccountNo] = useState<number | null>(null);
  const [bankName, setBankName] = useState<string>("");
  const accessToken = localStorage.getItem("accessToken");

  const navigate = useNavigate();

  const parseDateString = (dateString: string): Date => {
    const parts = dateString.match(/(\d{4})년 (\d{1,2})월 (\d{1,2})일/);
    if (parts) {
      const year = parseInt(parts[1], 10);
      const month = parseInt(parts[2], 10) - 1;
      const day = parseInt(parts[3], 10);
      return new Date(year, month, day);
    }
    return new Date();
  };

  const getAccount = useCallback(async () => {
    try {
      if (!accessToken) {
        throw new Error("Access token not found");
      }
      const accountInfo: GetAccountInfoDto = await getAccountInfo(accessToken);
      const accountNo = accountInfo.data.accountNo;
      const bankName = accountInfo.data.bankName;

      setAccountNo(accountNo);
      setBankName(bankName);
    } catch (error) {
      console.error("Error fetching account info:", error);
    }
  }, [accessToken]);

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

    getAccount();

    fetchInvitation();
  }, [invitationId, getAccount]);

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

  const markDate = parseDateString(invitationData.date);

  const handleEdit = () => {
    navigate(`/invite/info/edit/${invitationId}`);
  };

  const handleDelete = async () => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (confirmDelete) {
      try {
        await deleteFormalInvitation(Number(invitationId));
        navigate("/invitation/storage");
      } catch (error) {
        console.error("청첩장 삭제 중 오류 발생:", error);
        alert("삭제에 실패했습니다.");
      }
    }
  };

  return (
    <div className="relative font-nanum min-w-[1500px] w-full">
      <div
        className="absolute inset-0 bg-cover bg-center"
        style={{
          backgroundImage: `url(${pinkpaper})`,
          opacity: 0.2,
        }}
      />
      <div className="relative z-10 text-center w-full">
        <button className="flex justify-start ml-20 absolute mt-10 bg-transparent">
          <a href="/invitation/storage">
            <p className="text-[#C1A56C]">{"<<"} 뒤로가기</p>
          </a>
        </button>

        <div className="absolute mt-10 right-20 flex space-x-4">
          <button
            className="bg-transparent text-[#C1A56C] px-2 py-2 rounded"
            onClick={handleEdit}
          >
            수정
          </button>
          <span className="text-[#C1A56C] py-2">|</span>
          <button
            className="bg-transparent text-[#C1A56C] px-2 py-2 rounded"
            onClick={handleDelete}
          >
            삭제
          </button>
        </div>

        <div className="text-2xl font-semibold letter-space pt-36 text-[#800000]">
          WEDDING INVITATION
        </div>

        <div className="letter-space w-full">
          <p className="text-3xl mt-10 flex justify-center text-[#222B45]">
            {invitationData.groomLastName}
            {invitationData.groomFirstName}{" "}
            <span className="w-8 mx-10">
              <img src={leaf} alt="" />
            </span>{" "}
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
            <div className="w-[480px] min-h-[400px] bg-[#FDF5E6] mb-20">
              <div className="flex justify-center">
                <img src={flower_sm} alt="꽃" className="w-10 mt-5 mb-5" />
              </div>
              <p className="text-[#B3B3B3] letter-space-sm mb-5">INVITATION</p>
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
                <p className="mb-10">
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

          <div className="flex justify-center items-center">
            <div className="w-[480px]">
              <div className="text-center">
                <p className="text-[#B3B3B3] letter-space-sm mb-5">CALENDAR</p>
                <div className="border border-gray-200 mx-10 mb-10"></div>
                <p className="text-[18px] mb-5">{invitationData.date}</p>
                <div className="flex justify-center mb-20">
                  <Calendar
                    value={markDate}
                    locale="en-US"
                    formatDay={(_, date) => dayjs(date).format("DD")}
                    tileClassName={({ date }) => {
                      if (
                        date.getFullYear() === markDate.getFullYear() &&
                        date.getMonth() === markDate.getMonth() &&
                        date.getDate() === markDate.getDate()
                      ) {
                        return "highlight";
                      }
                      return null;
                    }}
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="flex justify-center">
            <div className="w-[480px]">
              <div className="text-center">
                <p className="text-[#B3B3B3] letter-space-sm mb-2">LOCATION</p>
                <p className="text-[#86626E] text-sm letter-space-sm mb-5">
                  오시는 길
                </p>
                <div className="border border-gray-200 mx-10 mb-10"></div>
                <p className="text-[18px]">{invitationData.address}</p>
                <p className="mb-10 text-[18px]">
                  {invitationData.weddingHall} {invitationData.addressDetail}
                </p>
              </div>
            </div>
          </div>

          <div className="flex justify-center">
            <div className="mb-20 w-[400px]">
              <InvitationMap
                latitude={invitationData.latitude}
                longitude={invitationData.longitude}
              />
            </div>
          </div>

          <div className="flex justify-center mb-10">
            <div className="w-[480px] flex justify-center">
              <img src={rose} alt="장미 한 송이" className="w-10" />
            </div>
          </div>

          <div className="text-[18px]">
            <p className="text-center">참석이 어려우신 분들은</p>
            <p className="text-center">축하의 마음을 전달해 주세요.</p>
          </div>

          <div className="flex justify-center mt-5">
            <div className="w-[400px] h-[70px] bg-[#F4F0EB] flex items-center justify-between cursor-pointer p-2">
              <p className="text-[18px] flex-1 text-center">축의금 계좌번호</p>
            </div>
          </div>
          <div className="flex justify-center">
            <div className="w-[400px] bg-white border border-gray-200 p-2">
              <p className="text-[15px]">
                {bankName} {accountNo}
              </p>
            </div>
          </div>
        </div>

        <div className="flex justify-center mt-20">
          <div className="w-[560px] h-[100px] bg-[#F4F0EB] flex items-center cursor-pointer p-2 mb-40">
            <div className="flex-1 border-r border-gray-300 h-full flex flex-col items-center justify-center">
              <img src={kakaoicon} alt="카톡 아이콘" className="w-9 mb-2" />
              <p className="text-sm">카카오톡 공유</p>
            </div>
            <div
              className="border-l border-gray-300 h-full"
              style={{ width: "1px" }}
            ></div>
            <div className="flex-1 flex flex-col items-center justify-center">
              <img src={copyicon} alt="복사 아이콘" className="w-9 mb-2" />
              <p className="text-sm">링크(URL) 복사</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StorageDetail;
