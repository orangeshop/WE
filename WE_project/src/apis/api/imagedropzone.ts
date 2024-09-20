import api from "../utils/instance";

export type ImageDto = {
  url: File;
};

export const inputImage = async (
  invitationId: string,
  dto: ImageDto
): Promise<void> => {
  try {
    const formData = new FormData();
    formData.append("file", dto.url);

    const response = await api.post(
      `/invitation/formal/file/${invitationId}`,
      formData,
      {}
    );

    return response.data;
  } catch (error) {
    console.error("이미지 업로드 중 오류 발생:", error);
    throw error;
  }
};
