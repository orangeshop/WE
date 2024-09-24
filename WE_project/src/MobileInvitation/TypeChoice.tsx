import React from "react";
import Navbar from "../Components/Navbar";
import invitation_card from "../assets/images/invitation_card.jpg";
import { createFormalInvitation } from "../apis/api/infotypeinvitation";

const TypeChoice: React.FC = () => {
  const handleCreateInvitation = async () => {
    try {
      const accessToken = localStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("Access token not found");
      }
      const invitationId = await createFormalInvitation(accessToken, {});
      window.location.href = `/invite/info/${invitationId}`;
    } catch (error) {
      console.error("Error creating invitation:", error);
    }
  };

  return (
    <div className="font-nanum">
      <Navbar isScrollSensitive={false} />
      <div className="justify-between mt-20">
        <div className="flex gap-40">
          <div>
            <img
              src={invitation_card}
              alt="invitation_card"
              className="w-80 h-auto mb-5"
            />
            <div className="text-center font-default">
              <p className="text-2xl mb-3 font-bold">정보형 청첩장</p>
              <p>필요한 정보들을 간단히 입력하기만 하면</p>
              <p>예쁜 모바일 청첩장이 완성돼요.</p>
              <div className="flex gap-3 justify-center">
                <button
                  type="submit"
                  className="py-2 px-5 rounded-3xl text-md bg-[#FFECCA] mt-5 font-default"
                  onClick={() => (window.location.href = "/invite/info/sample")}
                >
                  샘플보기
                </button>
                <button
                  type="submit"
                  className="py-2 px-5 rounded-3xl text-md bg-[#FFD0DE] mt-5 font-default"
                  onClick={handleCreateInvitation}
                >
                  제작하기
                </button>
              </div>
            </div>
          </div>
          <div className="border-l border-gray-300 h-100"></div>
          <div>
            <img
              src={invitation_card}
              alt="invitation_card"
              className="w-80 h-auto mb-5"
            />
            <div className="text-center font-default">
              <p className="text-2xl mb-3 font-bold">자유형 청첩장</p>
              <p>처음부터 끝까지, 내가 원하는 대로 커스텀한</p>
              <p>나만의 모바일 청첩장을 만들 수 있어요.</p>
              <div className="flex gap-3 justify-center">
                <button
                  type="submit"
                  className="py-2 px-5 rounded-3xl text-md bg-[#FFECCA] mt-5"
                  onClick={() => (window.location.href = "/invite/free/sample")}
                >
                  샘플보기
                </button>
                <button
                  type="submit"
                  className="py-2 px-5 rounded-3xl text-md bg-[#FFD0DE] mt-5"
                  onClick={() => (window.location.href = "/invite/free")}
                >
                  제작하기
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TypeChoice;
