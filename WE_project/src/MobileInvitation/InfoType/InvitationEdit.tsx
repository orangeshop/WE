import React, { useState, useRef, useEffect } from "react";
import Navbar from "../../Components/Navbar";
import ImageDropzone from "./ImageDropzone";
import HusbandInfo from "./HusbandInfo";
import BrideInfo from "./BrideInfo";
import GreetingsSection from "./Greetings";
import LocationAndDate from "./LocationAndDate";
import { inputImage } from "../../apis/api/imagedropzone";
import {
  getFormalInvitation,
  BirthOrder,
  Timezone,
} from "../../apis/api/getinfotypeinvitation";
import { useParams } from "react-router-dom";
import moment from "moment";
import "moment/locale/ko";

interface HusbandInfoHandle {
  submit: () => Promise<void>;
}

interface BrideInfoHandle {
  submit: () => Promise<void>;
}

interface GreetingsHandle {
  submit: () => Promise<void>;
}

interface LocationAndDateHandle {
  submit: () => Promise<void>;
}

const InvitationEdit: React.FC = () => {
  const [selectedImage, setSelectedImage] = useState<File | null>(null);
  const [imageSrc, setImageSrc] = useState<string | null>(null);
  const [greetings, setGreetings] = useState<string>("");
  const [title, setTitle] = useState<string>("");

  const [groomInfo, setGroomInfo] = useState({
    lastName: "",
    firstName: "",
    birthOrder: "" as "" | BirthOrder,
    fatherLastName: "",
    fatherFirstName: "",
    motherLastName: "",
    motherFirstName: "",
  });

  const [brideInfo, setBrideInfo] = useState({
    lastName: "",
    firstName: "",
    birthOrder: "" as "" | BirthOrder,
    fatherLastName: "",
    fatherFirstName: "",
    motherLastName: "",
    motherFirstName: "",
  });

  const [calendarValue, setCalendarValue] = useState<Date | null>(null);
  const [weddingHall, setWeddingHall] = useState<string>("");
  const [addressDetail, setAddressDetail] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [latitude, setLatitude] = useState<number>(0);
  const [longitude, setLongitude] = useState<number>(0);
  const [time, setTime] = useState({
    day: Timezone.AM,
    hour: 1,
    minute: 0,
  });

  const husbandInfoRef = useRef<HusbandInfoHandle | null>(null);
  const brideInfoRef = useRef<BrideInfoHandle | null>(null);
  const greetingsRef = useRef<GreetingsHandle | null>(null);
  const locationAndDateRef = useRef<LocationAndDateHandle | null>(null);

  const { invitationId } = useParams<{ invitationId: string }>();
  useEffect(() => {
    const loadData = async () => {
      if (invitationId) {
        try {
          const data = await getFormalInvitation(Number(invitationId));
          setTitle(data.title);
          setGreetings(data.greetings);
          setImageSrc(data.url);

          setGroomInfo({
            lastName: data.groomLastName,
            firstName: data.groomFirstName,
            birthOrder: data.groomBirthOrder as BirthOrder,
            fatherLastName: data.groomFatherLastName,
            fatherFirstName: data.groomFatherFirstName,
            motherLastName: data.groomMotherLastName,
            motherFirstName: data.groomMotherFirstName,
          });

          setBrideInfo({
            lastName: data.brideLastName,
            firstName: data.brideFirstName,
            birthOrder: data.brideBirthOrder as BirthOrder,
            fatherLastName: data.brideFatherLastName,
            fatherFirstName: data.brideFatherFirstName,
            motherLastName: data.brideMotherLastName,
            motherFirstName: data.brideMotherFirstName,
          });

          const parsedDate = moment(data.date, "YYYY년 M월 D일 dddd");
          if (parsedDate.isValid()) {
            setCalendarValue(parsedDate.toDate());
          } else {
            console.error("Invalid date format:", data.date);
            setCalendarValue(null);
          }

          setWeddingHall(data.weddingHall);
          setAddressDetail(data.addressDetail);
          setAddress(data.address);
          setLatitude(data.latitude);
          setLongitude(data.longitude);
          setTime({
            day: data.timezone,
            hour: data.hour,
            minute: data.minute,
          });
        } catch (error) {
          console.error("Error loading invitation data:", error);
        }
      }
    };

    loadData();
  }, [invitationId]);

  const handleImageChange = (file: File | null, imageSrc: string | null) => {
    setSelectedImage(file);
    setImageSrc(imageSrc);
  };

  const imageUpload = async () => {
    if (selectedImage && invitationId) {
      const dto = {
        url: selectedImage,
        title: title,
      };

      try {
        await inputImage(invitationId, dto);
      } catch (error) {
        console.error("Error uploading image:", error);
      }
    } else {
      alert("Please select an image.");
    }
  };

  const handleUpdate = async () => {
    const isImageChanged = selectedImage !== null;

    if (isImageChanged) {
      await imageUpload();
    }

    if (husbandInfoRef.current) {
      await husbandInfoRef.current.submit();
    } else {
      alert("신랑 정보를 먼저 입력해 주세요.");
      return;
    }

    if (brideInfoRef.current) {
      await brideInfoRef.current.submit();
    } else {
      alert("신부 정보를 먼저 입력해 주세요.");
      return;
    }

    if (greetingsRef.current) {
      await greetingsRef.current.submit();
    } else {
      alert("인사말을 먼저 작성해 주세요.");
      return;
    }

    if (locationAndDateRef.current) {
      await locationAndDateRef.current.submit();
    } else {
      alert("예식 장소와 일자를 먼저 입력해 주세요.");
      return;
    }

    window.location.href = `/invitation/storage/${invitationId}`;
  };

  return (
    <div className="font-nanum">
      <Navbar isScrollSensitive={false} />
      <div className="mt-40 font-default flex flex-col items-center justify-center">
        <input
          id="title"
          name="title"
          type="text"
          placeholder="Enter invitation title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="w-full mb-20 text-center px-4 py-3 border-b text-md focus:outline-none focus:border-gray-700 bg-[#fcfaf5]"
          required
        />
        <ImageDropzone
          onImageChange={handleImageChange}
          initialImage={imageSrc}
        />
        <div className="w-full text-center">
          <p className="mt-20 text-md font-semibold">사진을 선택해 주세요.</p>
          <div className="mt-20 border border-gray-200"></div>
        </div>
        <div>
          <HusbandInfo ref={husbandInfoRef} initialGroomInfo={groomInfo} />
          <BrideInfo ref={brideInfoRef} initialBrideInfo={brideInfo} />
          <GreetingsSection
            ref={greetingsRef}
            value={greetings}
            onChange={(e) => setGreetings(e.target.value)}
          />
        </div>
        <LocationAndDate
          ref={locationAndDateRef}
          initialCalendarValue={calendarValue}
          initialWeddingHall={weddingHall}
          initialAddressDetail={addressDetail}
          initialAddress={address}
          initialLatitude={latitude}
          initialLongitude={longitude}
          initialTime={time}
        />
        <div className="w-full flex justify-end gap-3 mt-10 mb-10">
          <button
            className="w-24 h-10 text-sm rounded-md text-md bg-[#FFD0DE]"
            onClick={handleUpdate}
          >
            수정하기
          </button>
        </div>
      </div>
    </div>
  );
};

export default InvitationEdit;
