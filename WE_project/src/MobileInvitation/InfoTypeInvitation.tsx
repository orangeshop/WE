import React, { useState, useRef, useEffect } from "react";
import Navbar from "../Components/Navbar";
import Cropper from "cropperjs";
import "cropperjs/dist/cropper.min.css";
import dropzone from "../assets/images/dropzone.jpg";

const InfoTypeInvitation: React.FC = () => {
  const [selectedImage, setSelectedImage] = useState<File | null>(null);
  const [imageSrc, setImageSrc] = useState<string | null>(null);
  const [showCropper, setShowCropper] = useState(false);
  const cropperRef = useRef<HTMLImageElement>(null);
  const [cropper, setCropper] = useState<Cropper | null>(null);
  const [aspectRatio, setAspectRatio] = useState<number>(1);
  const [brideOrder, setBrideOrder] = useState<string>("");

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

  const handleBrideOrderChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setBrideOrder(event.target.value);
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
              id="husband--order"
              name="husband--order"
              value={brideOrder}
              onChange={handleBrideOrderChange}
              className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
                brideOrder === "" ? "text-gray-400" : "text-black"
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
            className="mt-5 custom-textarea px-4 py-2 border border-gray-400 focus:border-gray-400 bg-white text-gray-700"
            required
          />
          <div className="mt-20 border border-gray-200"></div>
        </div>

        <div className="mt-20">
          <p>예식장 위치 / 예식 일자를 작성해 주세요.</p>
        </div>
      </div>
    </div>
  );
};

export default InfoTypeInvitation;
