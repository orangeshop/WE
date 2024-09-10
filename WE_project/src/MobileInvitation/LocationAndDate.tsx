import React, { useState, useCallback } from "react";
import cal_2 from "../assets/images/cal_2.png";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import Modal from "react-modal";
import KakaoMap from "./KakaoMap";
import moment from "moment";
import "moment/locale/ko";

Modal.setAppElement("#root");

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];

interface Props {
  time_day: string;
  time_hour: string;
  time_minute: string;
  onDayChange: (event: React.ChangeEvent<HTMLSelectElement>) => void;
  onHourChange: (event: React.ChangeEvent<HTMLSelectElement>) => void;
  onMinuteChange: (event: React.ChangeEvent<HTMLSelectElement>) => void;
}

const LocationAndDate: React.FC<Props> = ({
  time_day,
  time_hour,
  time_minute,
  onDayChange,
  onHourChange,
  onMinuteChange,
}) => {
  const [calendarValue, setCalendarValue] = useState<Value>(null);
  const [modalIsOpen, setModalIsOpen] = useState<boolean>(false);

  const openModal = () => setModalIsOpen(true);
  const closeModal = () => setModalIsOpen(false);

  const onChangeCalendar = useCallback((newValue: Value) => {
    setCalendarValue(newValue);
  }, []);
  moment.locale("ko");

  const formatSelectedDates = () => {
    if (calendarValue === null) {
      return "";
    }
    if (Array.isArray(calendarValue)) {
      return calendarValue
        .map((date) =>
          date
            ? new Intl.DateTimeFormat("ko-KR", {
                year: "numeric",
                month: "long",
                day: "numeric",
                weekday: "long",
              }).format(date)
            : ""
        )
        .join(" - ");
    } else {
      return calendarValue
        ? new Intl.DateTimeFormat("ko-KR", {
            year: "numeric",
            month: "long",
            day: "numeric",
            weekday: "long",
          }).format(calendarValue)
        : "";
    }
  };

  return (
    <div>
      <div className="mt-20 mb-5">
        <p className="font-semibold text-center">
          예식장 위치 / 예식 일자를 작성해 주세요.
        </p>
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
        <Modal
          isOpen={modalIsOpen}
          onRequestClose={closeModal}
          contentLabel="달력 모달"
          className="modal"
          overlayClassName="overlay"
        >
          <div className="flex flex-col w-full h-full items-center justify-center">
            <Calendar
              onChange={onChangeCalendar}
              value={calendarValue || undefined}
              calendarType="gregory"
              formatDay={(locale, date) =>
                moment(date)
                  .locale(locale || "ko")
                  .format("DD")
              }
            />
            <button
              onClick={closeModal}
              className="px-3 h-10 rounded-md text-md bg-[#FFD0DE] text-gray-500 self-end mt-4"
            >
              선택
            </button>
          </div>
        </Modal>
      </div>
      <div className="w-full flex justify-between gap-3 mb-3">
        <select
          id="time_day"
          name="time_day"
          value={time_day}
          onChange={onDayChange}
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
          onChange={onHourChange}
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
          onChange={onMinuteChange}
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
  );
};

export default LocationAndDate;
