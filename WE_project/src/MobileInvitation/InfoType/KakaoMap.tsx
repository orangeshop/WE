import React, { useEffect, useState } from "react";
import { getFormalInvitation } from "../../apis/api/getinfotypeinvitation";
import { useParams } from "react-router-dom";

declare global {
  interface Window {
    kakao: any;
    daum: any;
  }
}

interface IAddr {
  address: string;
}

interface KakaoMapProps {
  onLocationChange: (
    address: string,
    latitude: number,
    longitude: number
  ) => void;
  initialAddress?: string;
}

const KakaoMap: React.FC<KakaoMapProps> = ({ onLocationChange, initialAddress }) => {
  const [map, setMap] = useState<any>(null);
  const [marker, setMarker] = useState<any>(null);
  const [showMap, setShowMap] = useState<boolean>(true);
  const [address, setAddress] = useState<string>(initialAddress || "");
  const { invitationId } = useParams();

  useEffect(() => {
    const fetchData = async () => {
      if (invitationId) {
        try {
          const response = await getFormalInvitation(Number(invitationId));
          setAddress(response.address || "");

          // latitude와 longitude 값이 존재할 경우 지도와 마커 초기화
          if (response.latitude && response.longitude) {
            const currentPos = new window.kakao.maps.LatLng(response.latitude, response.longitude);
            if (map) {
              map.panTo(currentPos);
              marker.setMap(null);
              marker.setPosition(currentPos);
              marker.setMap(map);
            }
          }
        } catch (error) {
          console.error(error);
        }
      }
    };
    fetchData();
  }, [invitationId, map, marker]);

  useEffect(() => {
    if (window.kakao && window.daum && showMap) {
      window.kakao.maps.load(() => {
        const container = document.getElementById("map");
        const options = {
          center: new window.kakao.maps.LatLng(33.450701, 126.570667),
          level: 4,
        };

        const mapInstance = new window.kakao.maps.Map(container, options);
        const markerInstance = new window.kakao.maps.Marker();

        setMap(mapInstance);
        setMarker(markerInstance);

        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
            (position) => {
              const currentPos = new window.kakao.maps.LatLng(
                position.coords.latitude,
                position.coords.longitude
              );

              mapInstance.setCenter(currentPos);
              markerInstance.setPosition(currentPos);
              markerInstance.setMap(mapInstance);
            },
            () => {
              console.error("Could not get current location.");
            }
          );
        } else {
          console.error("Geolocation is not supported by this browser.");
        }
      });
    } else if (!showMap) {
      setMap(null);
    }
  }, [showMap]);

  const onClickAddr = () => {
    if (window.daum && window.kakao) {
      new window.daum.Postcode({
        oncomplete: function (addrData: IAddr) {
          const geocoder = new window.kakao.maps.services.Geocoder();

          geocoder.addressSearch(
            addrData.address,
            function (result: any, status: any) {
              if (status === window.kakao.maps.services.Status.OK) {
                const currentPos = new window.kakao.maps.LatLng(
                  result[0].y,
                  result[0].x
                );

                setAddress(addrData.address);

                map.panTo(currentPos);

                marker.setMap(null);
                marker.setPosition(currentPos);
                marker.setMap(map);

                onLocationChange(addrData.address, result[0].y, result[0].x);
              } else {
                alert("주소를 찾을 수 없습니다.");
              }
            }
          );
        },
      }).open();
    } else {
      alert("카카오 주소 검색 API가 로드되지 않았습니다.");
    }
  };

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setShowMap(event.target.checked);
  };

  useEffect(() => {
    setAddress(initialAddress || "");
  }, [initialAddress]);

  return (
    <div className="w-full">
      <div className="flex justify-between">
        <input
          id="addr"
          value={address} // 상태 값으로 제어
          readOnly
          className="border border-gray-400 w-72 h-10 text-center"
          placeholder="주소를 입력하세요"
        />
        <button
          onClick={onClickAddr}
          className="py-2 w-24 rounded-md text-md bg-[#FFD0DE] text-sm"
        >
          검색
        </button>
      </div>

      <div className="mt-4">
        <label>
          <input
            type="checkbox"
            checked={showMap}
            onChange={handleCheckboxChange}
          />{" "}
          지도 표시
        </label>
      </div>

      {showMap && (
        <div
          id="map"
          style={{ width: "400px", height: "300px", marginTop: "20px" }}
        ></div>
      )}
    </div>
  );
};

export default KakaoMap;
