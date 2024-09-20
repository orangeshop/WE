import React, { useEffect, useState } from "react";
import {
  getFormalInvitation,
  GetFormalInvitationDto,
} from "../apis/api/getinfotypeinvitation";

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

      setInvitations(fetchedInvitations);
      setLoading(false);
    };

    fetchAllInvitations();
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      {invitations.length > 0 ? (
        <ul className="grid grid-cols-3 gap-4">
          {invitations.map((invitation) => (
            <li key={invitation.invitationId} className="flex justify-center">
              <a href={`/invitation/storage/${invitation.invitationId}`}>
                <img
                  src={invitation.url}
                  alt="thumbnail"
                  className="w-48 h-48 object-cover"
                />
              </a>
            </li>
          ))}
        </ul>
      ) : (
        <p>청첩장이 없습니다.</p>
      )}
    </div>
  );
};

export default Storage;
