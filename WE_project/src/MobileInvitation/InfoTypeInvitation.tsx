import React, { useState, useCallback } from "react";
import Navbar from "../Components/Navbar";
import ImageDropzone from "./ImageDropzone";
import KakaoMap from "./KakaoMap";
import HusbandInfo from "./HusbandInfo";
import BrideInfo from "./BrideInfo";
import Greetings from "./Greetings";
import cal_2 from "../assets/images/cal_2.png";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import Modal from "react-modal";

Modal.setAppElement("#root");

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];

const InfoTypeInvitation: React.FC = () => {
  const [, setSelectedImage] = useState<File | null>(null);
  const [, setImageSrc] = useState<string | null>(null);
  const [brideOrder, setBrideOrder] = useState<string>("");
  const [greetings, setGreetings] = useState<string>("");
  const [husbandOrder, setHusbandOrder] = useState<string>("");
  const [time_day, setDayTime] = useState<string>("");
  const [time_hour, setHourTime] = useState<string>("");
  const [time_minute, setMinuteTime] = useState<string>("");
  const [calendarValue, setCalendarValue] = useState<Value>(null);
  const [modalIsOpen, setModalIsOpen] = useState<boolean>(false);

  const openModal = () => setModalIsOpen(true);
  const closeModal = () => setModalIsOpen(false);

  const onChangeCalendar = useCallback((newValue: Value) => {
    setCalendarValue(newValue);
  }, []);

  const formatSelectedDates = () => {
    if (calendarValue === null) {
      return "";
    }
    if (Array.isArray(calendarValue)) {
      return calendarValue
        .map((date) =>
          date?.toLocaleDateString("ko-KR", {
            year: "numeric",
            month: "long",
            day: "numeric",
            weekday: "long",
          })
        )
        .join(" - ");
    } else {
      return calendarValue?.toLocaleDateString("ko-KR", {
        year: "numeric",
        month: "long",
        day: "numeric",
        weekday: "long",
      });
    }
  };

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

  return (
    <div className="font-nanum">
      <Navbar isScrollSensitive={false} />
      <div className="font-default flex flex-col items-center justify-center">
        <ImageDropzone onImageChange={handleImageChange} />
        <div className="w-full text-center">
          <p className="mt-5 text-md">메인 사진을 선택해 주세요.</p>
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

        <div className="mt-20 mb-5">
          <p>예식장 위치 / 예식 일자를 작성해 주세요.</p>
        </div>
        <div className="relative w-full mb-3 mt-5 px-2 py-2 border border-gray-400 focus:border-gray-400 text-center text-gray-400 bg-white">
          <span className={`block ${calendarValue ? "hidden" : ""}`}>
            예식일자
          </span>
          <img
            src={cal_2}
            alt="cal_2"
            width={30}
            className="absolute right-2 top-1/2 transform -translate-y-1/2 cursor-pointer"
            onClick={openModal}
          />
          <p className={calendarValue ? "text-black" : "text-gray-400"}>
            {calendarValue ? formatSelectedDates() : ""}
          </p>
          <div className="w-full">
            <Modal
              isOpen={modalIsOpen}
              onRequestClose={closeModal}
              contentLabel="달력 모달"
              className="flex modal justify-center"
              overlayClassName="overlay"
            >
              <Calendar
                onChange={onChangeCalendar}
                value={calendarValue || undefined}
              />
              <button onClick={closeModal}>선택</button>
            </Modal>
          </div>
        </div>
        <div className="w-full flex justify-between gap-3 mb-3">
          <select
            id="time_day"
            name="time_day"
            value={time_day}
            onChange={timeDayChange}
            className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
              time_day === "" ? "text-gray-400" : "text-black"
            }`}
          >
            <option value="am">오전</option>
            <option value="pm">오후</option>
          </select>

          <select
            id="time_hour"
            name="time_hour"
            value={time_hour}
            onChange={timeHourChange}
            className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
              time_hour === "" ? "text-gray-400" : "text-black"
            }`}
          >
            <option value="one">1시</option>
            <option value="two">2시</option>
            <option value="three">3시</option>
            <option value="four">4시</option>
            <option value="five">5시</option>
            <option value="six">6시</option>
            <option value="seven">7시</option>
            <option value="eight">8시</option>
            <option value="nine">9시</option>
            <option value="ten">10시</option>
            <option value="eleven">11시</option>
            <option value="twelve">12시</option>
          </select>

          <select
            id="time_minute"
            name="time_minute"
            value={time_minute}
            onChange={timeMinuteChange}
            className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
              time_minute === "" ? "text-gray-400" : "text-black"
            }`}
          >
            <option value="zero">00분</option>
            <option value="thirty">30분</option>
          </select>
        </div>
        <div className="w-full">
          <input
            id="weddinghall-name"
            name="weddinghall-name"
            type="text"
            placeholder="예식장 명"
            className="mb-3 w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
            required
          />
          <input
            id="hallfloor-name"
            name="hallfloor-name"
            type="text"
            placeholder="층과 홀"
            className="mb-3 w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
            required
          />
          <KakaoMap />
        </div>
      </div>
    </div>
  );
};

export default InfoTypeInvitation;
