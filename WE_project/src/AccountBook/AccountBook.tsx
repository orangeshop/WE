import React, { useEffect, useState } from "react";
import { getAccountBook, GetAccountBook } from "../apis/api/getaccountbook";
import Navbar from "../Components/Navbar";

const AccountBook: React.FC = () => {
  const [accountData, setAccountData] = useState<GetAccountBook | null>(null);

  const accessToken = localStorage.getItem("accessToken");

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (accessToken) {
          const data = await getAccountBook(accessToken);
          setAccountData(data);
        }
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();
  }, [accessToken]);

  // 총 합계 및 신부측, 신랑측 합계 계산
  const totalCharge = accountData?.data.reduce(
    (acc, item) => acc + item.charge,
    0
  );

  const brideTotal = accountData?.data
    .filter(item => item.isBride === true)
    .reduce((acc, item) => acc + item.charge, 0);

  const groomTotal = accountData?.data
    .filter(item => item.isBride === false)
    .reduce((acc, item) => acc + item.charge, 0);

  return (
    <div className="font-nanum">
      <Navbar isScrollSensitive={false} />
      <div>
        {accountData && accountData.data.length > 0 ? (
          <div>
            <table className="table-auto border-collapse w-full text-left mt-4">
              <thead>
                <tr>
                  <th className="border px-4 py-2 bg-gray-200">순번</th>
                  <th className="border px-4 py-2 bg-gray-200">성함</th>
                  <th className="border px-4 py-2 bg-gray-200">금액(원)</th>
                  <th className="border px-4 py-2 bg-gray-200">구분</th>
                  <th className="border px-4 py-2 bg-gray-200">메시지</th>
                </tr>
              </thead>
              <tbody>
                {accountData.data.map((item, index) => (
                  <tr key={item.id} className="hover:bg-gray-100">
                    <td className="border px-4 py-2">{index + 1}</td>
                    <td className="border px-4 py-2">{item.memberInfo.nickname}</td>
                    <td className="border px-4 py-2">
                      {item.charge.toLocaleString()}
                    </td>
                    <td className="border px-4 py-2">
                      {item.isBride === true
                        ? "신부"
                        : item.isBride === false
                        ? "신랑"
                        : "신랑신부 모두"}
                    </td>
                    <td className="border px-4 py-2">
                      {item.message ? item.message : "메시지가 없습니다."}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            <div className="mt-8">
              <table className="table-auto border-collapse w-full text-left mt-2">
                <thead>
                  <tr>
                    <th className="border px-4 py-2 bg-gray-200">구분</th>
                    <th className="border px-4 py-2 bg-gray-200">금액(원)</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td className="border px-4 py-2">총 합계</td>
                    <td className="border px-4 py-2">{totalCharge?.toLocaleString()}</td>
                  </tr>
                  <tr>
                    <td className="border px-4 py-2">신부측 합계</td>
                    <td className="border px-4 py-2">{brideTotal?.toLocaleString() || 0}</td>
                  </tr>
                  <tr>
                    <td className="border px-4 py-2">신랑측 합계</td>
                    <td className="border px-4 py-2">{groomTotal?.toLocaleString() || 0}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        ) : (
          <p>등록된 축의금이 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default AccountBook;
