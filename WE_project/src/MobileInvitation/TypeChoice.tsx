import React from "react";
import Navbar from "../Components/Navbar";
import mockup from "../assets/images/mockup.png";
import { createFormalInvitation } from "../apis/api/infotypeinvitation";
import { IoIosLink } from "react-icons/io";
import { FaWandMagicSparkles } from "react-icons/fa6";

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
          <div className="flex gap-20">
            <img
              src={mockup}
              alt="invitation_card"
              className="w-[500px] h-auto mb-5"
            />
            <div className="mt-48">
              <p className="text-[40px] font-semibold mb-5">정보형 청첩장</p>
              <p className="text-[16px]">일생에 가장 아름다운 날,</p>
              <p className="text-[16px]">
                [ WE : ]에서 모바일 청첩장을 만들어보세요!
              </p>
              <div className="flex gap-3 mb-5">
                <button
                  type="submit"
                  className="py-3 px-7 rounded-md text-md bg-[#FFECCA] mt-5"
                  onClick={() => (window.location.href = "/invite/info/sample")}
                >
                  <div className="flex">
                    <IoIosLink size={20} className="mr-2" />
                    샘플보기
                  </div>
                </button>
                <button
                  type="submit"
                  className="py-3 px-7 rounded-md text-md bg-[#FFD0DE] mt-5"
                  onClick={handleCreateInvitation}
                >
                  <div className="flex">
                    <FaWandMagicSparkles size={20} className="mr-2" />
                    제작하기
                  </div>
                </button>
              </div>
              <p className="text-[12px]">
                * 필요한 정보만 입력하면 예쁜 모바일 청첩장이 만들어져요.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TypeChoice;
