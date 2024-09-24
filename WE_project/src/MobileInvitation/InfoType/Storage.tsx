import React, { useEffect, useState } from "react";
import Navbar from "../../Components/Navbar";
import {
  GetCoupleInvitationDto,
  getCoupleInvitation,
} from "../../apis/api/coupleinvitation";

const Storage: React.FC = () => {
  const [invitations, setInvitations] = useState<GetCoupleInvitationDto[]>([]);

  useEffect(() => {
    const fetchCoupleInvitations = async () => {
      try {
        const accessToken = localStorage.getItem("accessToken");
        if (!accessToken) {
          throw new Error("Access token not found");
        }
        const response = await getCoupleInvitation(accessToken);
        console.log(response);
        setInvitations(response);
      } catch (err) {
        console.error(err);
      }
    };

    fetchCoupleInvitations();
  }, []);

  return (
    <div className="font-nanum min-w-[1260px]">
      <Navbar isScrollSensitive={false} />
      <div className="p-4">
        <div className="space-y-4">
          {invitations.length > 0 ? (
            invitations.map((invitation) => (
              <div
                key={invitation.invitationId}
                className="border p-4 rounded-md shadow-md"
              >
                <h3 className="text-xl font-bold">{invitation.title}</h3>
                <p>Couple ID: {invitation.coupleId}</p>
                <p>URL: {invitation.url}</p>
              </div>
            ))
          ) : (
            <p>No invitations found.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default Storage;
