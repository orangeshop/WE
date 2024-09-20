import { useState, useCallback, useImperativeHandle, forwardRef } from "react";
import cal_2 from "../assets/images/cal_2.png";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import Modal from "react-modal";
import KakaoMap from "./KakaoMap";
import moment from "moment";
import "moment/dist/locale/ko";
import {
  inputDateLocation,
  DateLocationDto,
  Timezone,
} from "../apis/api/datelocation";
import { useParams } from "react-router-dom";

Modal.setAppElement("#root");

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];

const LocationAndDate = forwardRef((_, ref) => {
  const [calendarValue, setCalendarValue] = useState<Value>(null);
  const [modalIsOpen, setModalIsOpen] = useState<boolean>(false);
  const [timeDay, setTimeDay] = useState<string>("am");
  const [timeHour, setTimeHour] = useState<number>(1);
  const [timeMinute, setTimeMinute] = useState<string>("zero");
  const [wedding_hall, setWeddingHall] = useState<string>("");
  const [address_detail, setAddressDetail] = useState<string>("");
  const [latitude, setLatitude] = useState<number>(0);
  const [longitude, setLongitude] = useState<number>(0);
  const [address, setAddress] = useState<string>("");
  const { invitationId } = useParams();
  moment.locale("ko");

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
          date
            ? new Intl.DateTimeFormat("ko", {
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
        ? new Intl.DateTimeFormat("ko", {
            year: "numeric",
            month: "long",
            day: "numeric",
            weekday: "long",
          }).format(calendarValue)
        : "";
    }
  };

  const handleLocationChange = (addr: string, lat: number, lon: number) => {
    setAddress(addr);
    setLatitude(lat);
    setLongitude(lon);
  };

  const handleSubmit = async () => {
    if (!calendarValue || !wedding_hall || !address_detail) {
      alert("모든 필드를 채워주세요.");
      return;
    }

    const selectedDate = Array.isArray(calendarValue)
      ? calendarValue[0]
      : calendarValue;

    const date = moment(selectedDate).format("YYYY-MM-DD dddd");

    const timezone = timeDay === "am" ? Timezone.AM : Timezone.PM;
    const hour = timeHour;
    const minute = timeMinute === "zero" ? 0 : 30;

    const dto: DateLocationDto = {
      date,
      timezone,
      hour,
      minute,
      wedding_hall,
      address,
      address_detail,
      latitude,
      longitude,
    };

    try {
      if (dto && invitationId) {
        await inputDateLocation(invitationId, dto);
      }
    } catch (error) {
      console.error("예식 정보 저장 중 오류 발생:", error);
      alert("저장 중 오류가 발생했습니다.");
    }
  };

  useImperativeHandle(ref, () => ({
    submit: handleSubmit,
  }));

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

      {/* Time selection */}
      <div className="w-full flex justify-between gap-3 mb-3">
        <select
          id="time_day"
          name="time_day"
          value={timeDay}
          onChange={(e) => setTimeDay(e.target.value)}
          className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
            timeDay === "" ? "text-gray-400" : "text-black"
          }`}
        >
          <option value="am">오전</option>
          <option value="pm">오후</option>
        </select>

        <select
          id="time_hour"
          name="time_hour"
          value={timeHour}
          onChange={(e) => setTimeHour(parseInt(e.target.value, 10))} // 숫자로 변환
          className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
            timeHour === 0 ? "text-gray-400" : "text-black"
          }`}
        >
          <option value={1}>1시</option>
          <option value={2}>2시</option>
          <option value={3}>3시</option>
          <option value={4}>4시</option>
          <option value={5}>5시</option>
          <option value={6}>6시</option>
          <option value={7}>7시</option>
          <option value={8}>8시</option>
          <option value={9}>9시</option>
          <option value={10}>10시</option>
          <option value={11}>11시</option>
          <option value={12}>12시</option>
        </select>

        <select
          id="time_minute"
          name="time_minute"
          value={timeMinute}
          onChange={(e) => setTimeMinute(e.target.value)}
          className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
            timeMinute === "" ? "text-gray-400" : "text-black"
          }`}
        >
          <option value="zero">00분</option>
          <option value="thirty">30분</option>
        </select>
      </div>

      {/* Wedding hall and floor input */}
      <div className="w-full">
        <input
          id="weddinghall-name"
          name="weddinghall-name"
          type="text"
          placeholder="예식장 명"
          value={wedding_hall}
          onChange={(e) => setWeddingHall(e.target.value)}
          className="mb-3 w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="hallfloor-name"
          name="hallfloor-name"
          type="text"
          placeholder="층과 홀"
          value={address_detail}
          onChange={(e) => setAddressDetail(e.target.value)}
          className="mb-3 w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <KakaoMap onLocationChange={handleLocationChange} />
      </div>
    </div>
  );
});

export default LocationAndDate;
