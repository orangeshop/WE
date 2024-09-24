import React, { useEffect, useState } from "react";
import Navbar from "../../Components/Navbar";
import {
  getCoupleInvitation,
  GetCoupleInvitationDto,
} from "../../apis/api/coupleinvitation";

const Storage: React.FC = () => {
  const [invitations, setInvitations] = useState<GetCoupleInvitationDto[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchCoupleInvitations = async () => {
      try {
        const coupleId = localStorage.getItem("coupleId");
        const accessToken = localStorage.getItem("accessToken");

        if (coupleId && accessToken) {
          const fetchedInvitations = await getCoupleInvitation(accessToken);
          console.log("Fetched Invitations:", fetchedInvitations); // 응답 데이터 확인

          setInvitations(fetchedInvitations);
        } else {
          console.error("coupleId 또는 accessToken이 존재하지 않습니다.");
        }
      } catch (error) {
        console.error("청첩장 목록을 가져오는 중 오류 발생:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchCoupleInvitations();
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

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
