import api from "../utils/instance";

export enum BirthOrder {
  FIRST = "FIRST", //장남
  SECOND = "SECOND", //차남
  OTHER = "OTHER", //아들
}

export type GroomInfoDto = {
  lastName: string;
  firstName: string;
  birthOrder: BirthOrder;

  fatherLastName: string;
  fatherFirstName: string;
  motherLastName: string;
  motherFirstName: string;
};

export const inputGroomInfo = async (
  invitationId: string,
  dto: GroomInfoDto
): Promise<void> => {
  try {
    const formData = new FormData();
    formData.append("lastName", dto.lastName);
    formData.append("firstName", dto.firstName);
    formData.append("birthOrder", dto.birthOrder);
    formData.append("fatherLastName", dto.fatherLastName);
    formData.append("fatherFirstName", dto.fatherFirstName);
    formData.append("motherLastName", dto.motherLastName);
    formData.append("motherFirstName", dto.motherFirstName);

    const response = await api.post(
      `/invitation/formal/groom/${invitationId}`,
      formData,
      {}
    );

    console.log(response);
    return response.data;
  } catch (error) {
    console.error("신랑 정보 업로드 중 오류 발생:", error);
    throw error;
  }
};
