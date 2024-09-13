import React, { useState } from "react";
import Navbar from "../Components/Navbar";
import ImageDropzone from "./ImageDropzone";
import HusbandInfo from "./HusbandInfo";
import BrideInfo from "./BrideInfo";
import Greetings from "./Greetings";
import LocationAndDate from "./LocationAndDate";
import { inputImage } from "../apis/api/imagedropzone";
import { useParams } from "react-router-dom";

const InfoTypeInvitation: React.FC = () => {
  const [selectedImage, setSelectedImage] = useState<File | null>(null);
  const [, setImageSrc] = useState<string | null>(null);
  const [brideOrder, setBrideOrder] = useState<string>("");
  const [greetings, setGreetings] = useState<string>("");
  const [husbandOrder, setHusbandOrder] = useState<string>("");
  const [time_day, setDayTime] = useState<string>("");
  const [time_hour, setHourTime] = useState<string>("");
  const [time_minute, setMinuteTime] = useState<string>("");
  const { invitationId } = useParams();

  const handleHusbandOrderChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setHusbandOrder(event.target.value);
  };

  const handleBrideOrderChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setBrideOrder(event.target.value);
  };

  const timeDayChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setDayTime(event.target.value);
  };

  const timeHourChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setHourTime(event.target.value);
  };

  const timeMinuteChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setMinuteTime(event.target.value);
  };

  const handleImageChange = (file: File | null, imageSrc: string | null) => {
    setSelectedImage(file);
    setImageSrc(imageSrc);
  };

  const handleGreetingsChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setGreetings(event.target.value);
  };

  const ImageUpload = async () => {
    if (selectedImage && invitationId) {
      const dto = {
        url: selectedImage,
      };

      try {
        await inputImage(invitationId, dto);
        alert("이미지 업로드 완료!");
      } catch (error) {
        console.error("이미지 업로드 중 오류 발생:", error);
      }
    } else {
      alert("이미지가 선택되지 않았습니다.");
    }
  };

  return (
    <div className="font-nanum">
      <Navbar isScrollSensitive={false} />
      <div className="font-default flex flex-col items-center justify-center">
        <ImageDropzone onImageChange={handleImageChange} />
        <div className="w-full text-center">
          <p className="mt-5 text-md font-semibold">
            메인 사진을 선택해 주세요.
          </p>
          <div className="mt-20 border border-gray-200"></div>
        </div>
        <div>
          <HusbandInfo
            husbandOrder={husbandOrder}
            handleHusbandOrderChange={handleHusbandOrderChange}
          />
          <BrideInfo
            brideOrder={brideOrder}
            handleBrideOrderChange={handleBrideOrderChange}
          />
        </div>
        <Greetings value={greetings} onChange={handleGreetingsChange} />
        <LocationAndDate
          time_day={time_day}
          time_hour={time_hour}
          time_minute={time_minute}
          onDayChange={timeDayChange}
          onHourChange={timeHourChange}
          onMinuteChange={timeMinuteChange}
        />

        <div className="w-full flex justify-end gap-3 mt-10 mb-10">
          <button className="w-24 h-10 text-sm rounded-md text-md bg-[#FFECCA]">
            임시저장
          </button>
          <button
            className="w-24 h-10 text-sm rounded-md text-md bg-[#FFD0DE]"
            onClick={ImageUpload}
          >
            만들기
          </button>
        </div>
      </div>
    </div>
  );
};

export default InfoTypeInvitation;
