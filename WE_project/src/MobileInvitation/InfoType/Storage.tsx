import React, { useEffect, useState } from "react";
import Navbar from "../../Components/Navbar";
import {
  getFormalInvitation,
  GetFormalInvitationDto,
} from "../../apis/api/getinfotypeinvitation";

const Storage: React.FC = () => {
  const [invitations, setInvitations] = useState<GetFormalInvitationDto[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchAllInvitations = async () => {
      const fetchedInvitations: GetFormalInvitationDto[] = [];
      let invitationId = 1;
      let continueFetching = true;

      while (continueFetching) {
        try {
          const invitation = await getFormalInvitation(invitationId);

          if (invitation.url) {
            fetchedInvitations.push(invitation);
          }

          invitationId++;
        } catch (error) {
          console.error(
            `Error fetching invitation with ID ${invitationId}:`,
            error
          );
          continueFetching = false;
        }
      }

      setInvitations(fetchedInvitations.reverse());
      setLoading(false);
    };

    fetchAllInvitations();
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
