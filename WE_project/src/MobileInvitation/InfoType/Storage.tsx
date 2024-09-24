import React, { useEffect, useState } from "react";
import Navbar from "../../Components/Navbar";
import {
  GetCoupleInvitationDto,
  getCoupleInvitation,
} from "../../apis/api/coupleinvitation";

const Storage: React.FC = () => {
  const [invitations, setInvitations] = useState<GetCoupleInvitationDto[]>([]);
  const accessToken = localStorage.getItem("accessToken");

  useEffect(() => {
    const fetchCoupleInvitations = async () => {
      try {
        if (!accessToken) {
          throw new Error("Access token not found");
        }
        // 청첩장 정보를 가져옴
        const response = await getCoupleInvitation(accessToken);
        setInvitations(response);
      } catch (err) {
        console.error(err);
      }
    };

    fetchCoupleInvitations();
  }, [accessToken]); // accessToken이 변경될 때마다 다시 호출

  return (
    <div className="font-nanum min-w-[1260px]">
      <Navbar isScrollSensitive={false} />
      <div className="mt-40">
        {invitations.length > 0 ? (
          <ul className="grid grid-cols-3 gap-4 mb-20">
            {invitations.map((invitation) => (
              <li
                key={invitation.invitationId}
                className="flex justify-center mb-10"
              >
                <a
                  href={`/invitation/storage/${invitation.invitationId}`}
                  className="transform transition-transform duration-300 hover:scale-105"
                >
                  <img
                    src={invitation.url}
                    alt="thumbnail"
                    className="w-56 h-72 object-cover shadow-lg rounded-lg"
                  />
                  <p className="text-center mt-5">
                    {invitation.title
                      ? invitation.title
                      : `청첩장 ${invitation.invitationId}`}
                  </p>
                </a>
              </li>
            ))}
          </ul>
        ) : (
          <p>청첩장이 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default Storage;
