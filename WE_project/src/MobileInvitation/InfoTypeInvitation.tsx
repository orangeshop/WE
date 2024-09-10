import React, { useState, useRef, useEffect, useCallback } from "react";
import Navbar from "../Components/Navbar";
import Cropper from "cropperjs";
import "cropperjs/dist/cropper.min.css";
import dropzone from "../assets/images/dropzone.jpg";
import cal_2 from "../assets/images/cal_2.png";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import Modal from "react-modal";

Modal.setAppElement("#root");

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];

const InfoTypeInvitation: React.FC = () => {
  const [selectedImage, setSelectedImage] = useState<File | null>(null);
  const [imageSrc, setImageSrc] = useState<string | null>(null);
  const [showCropper, setShowCropper] = useState(false);
  const cropperRef = useRef<HTMLImageElement>(null);
  const [cropper, setCropper] = useState<Cropper | null>(null);
  const [aspectRatio, setAspectRatio] = useState<number>(1);
  const [brideOrder, setBrideOrder] = useState<string>("");
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

  useEffect(() => {
    if (cropperRef.current && imageSrc) {
      const newCropper = new Cropper(cropperRef.current, {
        aspectRatio: aspectRatio,
        viewMode: 1,
        autoCropArea: 1,
        responsive: true,
        background: false,
        cropBoxResizable: true,
        cropBoxMovable: true,
      });
      setCropper(newCropper);
      return () => {
        newCropper.destroy();
      };
    }
  }, [imageSrc, aspectRatio]);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];
      setImageSrc(URL.createObjectURL(file));
      setSelectedImage(file);
      setShowCropper(true);
    }
  };

  const handleCrop = () => {
    if (cropper) {
      const canvas = cropper.getCroppedCanvas();
      canvas.toBlob((blob) => {
        if (blob) {
          setSelectedImage(
            new File([blob], selectedImage?.name || "image.jpg", {
              type: blob.type,
            })
          );
          setImageSrc(URL.createObjectURL(blob));
        }
      });
      setShowCropper(false);
    }
  };

  const handleRemoveImage = () => {
    setSelectedImage(null);
    setImageSrc(null);
  };

  const handleAspectRatioChange = (ratio: number) => {
    if (cropper) {
      cropper.setAspectRatio(ratio);
      setAspectRatio(ratio);
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

  return (
    <div className="font-nanum">
      <Navbar isScrollSensitive={false} />
      <div className="font-default flex flex-col items-center justify-center">
        <div className="relative w-80 h-80 flex items-center justify-center border bg-gray-100 mt-40">
          {selectedImage ? (
            <div className="relative w-80 h-80 flex items-center justify-center">
              <img
                src={imageSrc || ""}
                alt="Uploaded"
                className="w-80 h-80 object-cover"
              />
              <button
                onClick={handleRemoveImage}
                className="absolute top-2 right-2 bg-white text-gray-600 hover:bg-red-100 rounded-full p-1.5 shadow-md transition-all duration-300 ease-in-out"
              >
                <svg
                  className="w-5 h-5"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M6 18L18 6M6 6l12 12"
                  />
                </svg>
              </button>
            </div>
          ) : (
            <label
              className="flex flex-col items-center justify-center cursor-pointer"
              htmlFor="file-input"
            >
              <input
                id="file-input"
                type="file"
                accept="image/*"
                onChange={handleFileChange}
                className="hidden"
              />
              <img src={dropzone} alt="dropzone" />
            </label>
          )}
        </div>

        {showCropper && (
          <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
            <div className="bg-white p-4 rounded-lg w-full max-w-lg relative">
              <div className="mb-4">
                <img
                  ref={cropperRef}
                  src={imageSrc || ""}
                  alt="Cropper"
                  style={{ width: "100%", height: "auto" }}
                />
              </div>
              <div className="flex justify-between mb-4">
                <button
                  onClick={() => handleAspectRatioChange(1)}
                  className={`px-4 py-2 ${
                    aspectRatio === 1 ? "bg-blue-500 text-white" : "bg-gray-200"
                  }`}
                >
                  1:1
                </button>
                <button
                  onClick={() => handleAspectRatioChange(3 / 4)}
                  className={`px-4 py-2 ${
                    aspectRatio === 3 / 4
                      ? "bg-blue-500 text-white"
                      : "bg-gray-200"
                  }`}
                >
                  3:4
                </button>
              </div>
              <div className="flex justify-end">
                <button
                  onClick={() => setShowCropper(false)}
                  className="px-4 py-2 bg-gray-200 rounded-lg mr-2"
                >
                  Cancel
                </button>
                <button
                  onClick={handleCrop}
                  className="px-4 py-2 bg-blue-500 text-white rounded-lg"
                >
                  Crop
                </button>
              </div>
            </div>
          </div>
        )}
        <p className="mt-5 text-md">메인 사진을 선택해 주세요.</p>

        <div>
          <div className="mt-20 border border-gray-200"></div>
          <p className="mt-20 mb-5 text-md text-center">
            신랑 측 정보를 작성해 주세요.
          </p>
          <div className="flex justify-between gap-5 mb-3">
            <input
              id="husband-last-name"
              name="husband-last-name"
              type="text"
              placeholder="신랑 성"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
            <input
              id="husband-name"
              name="husband-name"
              type="text"
              placeholder="신랑 이름"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
          </div>

          <div className="flex mb-3">
            <select
              id="husband-order"
              name="husband-order"
              value={husbandOrder}
              onChange={handleHusbandOrderChange}
              className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
                husbandOrder === "" ? "text-gray-400" : "text-black"
              }`}
            >
              <option value="" disabled hidden>
                신랑 서열 (장남 / 차남 / 아들)
              </option>
              <option value="first">장남</option>
              <option value="second">차남</option>
              <option value="son">아들</option>
            </select>
          </div>

          <div className="flex justify-between gap-5 mb-3">
            <input
              id="husband-father-last-name"
              name="husband-father-last-name"
              type="text"
              placeholder="신랑 아버님 성"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
            <input
              id="husband-father-name"
              name="husband-father-name"
              type="text"
              placeholder="신랑 아버님 이름"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
          </div>
          <div className="flex justify-between gap-5 mb-3">
            <input
              id="husband-mother-last-name"
              name="husband-mother-last-name"
              type="text"
              placeholder="신랑 어머님 성"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
            <input
              id="husband-mother-name"
              name="husband-mother-name"
              type="text"
              placeholder="신랑 어머님 이름"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
          </div>
        </div>

        <div className="mb-20">
          <p className="mt-20 mb-5 text-md text-center">
            신부 측 정보를 작성해 주세요.
          </p>
          <div className="flex justify-between gap-5 mb-3">
            <input
              id="bride-last-name"
              name="bride-last-name"
              type="text"
              placeholder="신부 성"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
            <input
              id="bride-name"
              name="bride-name"
              type="text"
              placeholder="신부 이름"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
          </div>

          <div className="flex mb-3">
            <select
              id="bride-order"
              name="bride-order"
              value={brideOrder}
              onChange={handleBrideOrderChange}
              className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
                brideOrder === "" ? "text-gray-400" : "text-black"
              }`}
            >
              <option value="" disabled hidden>
                신부 서열 (장녀 / 차녀 / 딸)
              </option>
              <option value="first">장녀</option>
              <option value="second">차녀</option>
              <option value="son">딸</option>
            </select>
          </div>

          <div className="flex justify-between gap-5 mb-3">
            <input
              id="bride-father-last-name"
              name="bride-father-last-name"
              type="text"
              placeholder="신부 아버님 성"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
            <input
              id="bride-father-name"
              name="bride-father-name"
              type="text"
              placeholder="신부 아버님 이름"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
          </div>
          <div className="flex justify-between gap-5 mb-3">
            <input
              id="bride-mother-last-name"
              name="bride-mother-last-name"
              type="text"
              placeholder="신부 어머님 성"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
            <input
              id="bride-mother-name"
              name="bride-mother-name"
              type="text"
              placeholder="신부 어머님 이름"
              className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
              required
            />
          </div>
          <div className="mt-20 border border-gray-200"></div>
        </div>

        <div className="w-full text-center">
          <p>인사말을 작성해 주세요.</p>
          <textarea
            id="greetings"
            name="greetings"
            placeholder="인사말 작성"
            className="mt-5 custom-textarea px-4 py-2 border border-gray-400 focus:border-gray-400 bg-white"
            required
          />
          <div className="mt-20 border border-gray-200"></div>
        </div>

        <div className="mt-20">
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

          <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            contentLabel="달력 모달"
            className="modal"
            overlayClassName="overlay"
          >
            <Calendar
              onChange={onChangeCalendar}
              value={calendarValue || undefined}
            />
            <button onClick={closeModal}>선택</button>
          </Modal>
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
      </div>
    </div>
  );
};

export default InfoTypeInvitation;
