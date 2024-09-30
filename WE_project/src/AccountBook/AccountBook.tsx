import React, { useEffect, useState } from "react";
import { getAccountBook, GetAccountBook } from "../apis/api/getaccountbook";
import Navbar from "../Components/Navbar";
import { Line } from "react-chartjs-2";
import { Doughnut } from "react-chartjs-2";
import {
  Chart as ChartJS,
  LineElement,
  PointElement,
  LinearScale,
  CategoryScale,
  ArcElement,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(
  LineElement,
  PointElement,
  LinearScale,
  CategoryScale,
  ArcElement,
  Tooltip,
  Legend
);

const AccountBook: React.FC = () => {
  const [accountData, setAccountData] = useState<GetAccountBook | null>(null);
  const [sortedData, setSortedData] = useState<GetAccountBook | null>(null);
  const [sortOrder, setSortOrder] = useState<"asc" | "desc">("asc");

  const accessToken = localStorage.getItem("accessToken");

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (accessToken) {
          const data = await getAccountBook(accessToken);
          setAccountData(data);
          setSortedData(data);
        }
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();
  }, [accessToken]);

  const totalCharge = accountData?.data.reduce(
    (acc, item) => acc + item.charge,
    0
  );

  const brideTotal = accountData?.data
    .filter((item) => item.isBride === true)
    .reduce((acc, item) => acc + item.charge, 0);

  const groomTotal = accountData?.data
    .filter((item) => item.isBride === false)
    .reduce((acc, item) => acc + item.charge, 0);

  const chartColors = ["hsl(0, 100%, 85%)", "hsl(210, 100%, 85%)"];

  const sortedChartData = sortedData ? [...sortedData.data] : [];

  const brideData = sortedChartData
    .filter((item) => item.isBride === true)
    .map((item) => item.charge);

  const groomData = sortedChartData
    .filter((item) => item.isBride === false)
    .map((item) => item.charge);

  const chartData = {
    labels: sortedChartData.map((item) => item.memberInfo.nickname),
    datasets: [
      {
        label: "신부측 금액",
        data: brideData,
        borderColor: chartColors[0],
        backgroundColor: chartColors[0],
        fill: false,
      },
      {
        label: "신랑측 금액",
        data: groomData,
        borderColor: chartColors[1],
        backgroundColor: chartColors[1],
        fill: false,
      },
    ],
  };

  const doughnutData = {
    labels: ["신부측", "신랑측"],
    datasets: [
      {
        data: [brideTotal || 0, groomTotal || 0],
        backgroundColor: chartColors,
        hoverOffset: 4,
      },
    ],
  };

  const chartOptions = {
    plugins: {
      tooltip: {
        callbacks: {
          label: (tooltipItem: any) => {
            const dataset = tooltipItem.dataset;
            const total = dataset.data.reduce(
              (acc: number, value: number) => acc + value,
              0
            );
            const currentValue = dataset.data[tooltipItem.dataIndex];
            const percentage = ((currentValue / total) * 100).toFixed(2);
            return `${
              tooltipItem.label
            }: ${currentValue.toLocaleString()}원 (${percentage}%)`;
          },
        },
      },
    },
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

  const handleSortByCharge = () => {
    if (!accountData) return;

    const sorted = [...accountData.data].sort((a, b) => {
      if (sortOrder === "asc") {
        return b.charge - a.charge;
      } else {
        return a.charge - b.charge;
      }
    });

    setSortedData({ ...accountData, data: sorted });
    setSortOrder(sortOrder === "asc" ? "desc" : "asc");
  };

  return (
    <div className="font-nanum">
      <Navbar isScrollSensitive={false} />
      <div className="mt-24 mb-20">
        {sortedData && sortedData.data.length > 0 ? (
          <div>
            <h2 className="text-xl mb-4">청첩장 장부 내역</h2>
            <table className="table-auto border-collapse w-full text-left mt-4">
              <thead>
                <tr>
                  <th className="border px-4 py-2 bg-gray-200">순번</th>
                  <th className="border px-4 py-2 bg-gray-200">성함</th>
                  <th
                    className="border px-4 py-2 bg-gray-200 cursor-pointer"
                    onClick={handleSortByCharge}
                  >
                    금액(원)
                    {sortOrder === "asc" ? " ▲" : " ▼"}
                  </th>
                  <th className="border px-4 py-2 bg-gray-200">구분</th>
                  <th className="border px-4 py-2 bg-gray-200">메시지</th>
                </tr>
              </thead>
              <tbody>
                {sortedData.data.map((item, index) => (
                  <tr key={item.id} className="hover:bg-gray-100">
                    <td className="border px-4 py-2">{index + 1}</td>
                    <td className="border px-4 py-2">
                      {item.memberInfo.nickname}
                    </td>
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

            <div className="mb-8">
              <h2 className="text-xl mt-12 mb-4">청첩장 장부 차트</h2>
              <Line data={chartData} options={chartOptions} />{" "}
            </div>
            <div className="mb-8" style={{ width: "300px", height: "300px" }}>
              <h2 className="text-xl mb-4">전체 축의금 비율</h2>
              <Doughnut data={doughnutData} />
            </div>

            <div className="mt-20">
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
                    <td className="border px-4 py-2">
                      {totalCharge?.toLocaleString()}
                    </td>
                  </tr>
                  <tr>
                    <td className="border px-4 py-2">신부측 합계</td>
                    <td className="border px-4 py-2">
                      {brideTotal?.toLocaleString() || 0}
                    </td>
                  </tr>
                  <tr>
                    <td className="border px-4 py-2">신랑측 합계</td>
                    <td className="border px-4 py-2">
                      {groomTotal?.toLocaleString() || 0}
                    </td>
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
