import api from "../utils/instance";

export type GetCoupleInvitationDto = {
  invitationId: number;
  coupleId: number;
  title: string;
  url: string;
};

// accessToken만 인자로 받는 함수
export const getCoupleInvitation = async (
  accessToken: string
): Promise<GetCoupleInvitationDto[]> => {
  try {
    const response = await api.get<GetCoupleInvitationDto[]>(
      `/invitation/formal/couple`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    console.log("API Response:", response.data);

    return response.data; // 청첩장 목록 반환
  } catch (error) {
    console.error("커플 청첩장 조회 중 오류 발생:", error);
    throw error;
  }
};
