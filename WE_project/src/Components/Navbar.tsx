import React, { useEffect, useState } from "react";

interface NavbarProps {
  isScrollSensitive?: boolean;
}

const Navbar: React.FC<NavbarProps> = ({ isScrollSensitive = false }) => {
  const [navbarBackground, setNavbarBackground] = useState(false);
  const [dropdownVisible, setDropdownVisible] = useState(false);

  useEffect(() => {
    if (isScrollSensitive) {
      const handleScroll = () => {
        if (window.scrollY > 50) {
          setNavbarBackground(true);
        } else {
          setNavbarBackground(false);
        }
      };

      window.addEventListener("scroll", handleScroll);

      return () => {
        window.removeEventListener("scroll", handleScroll);
      };
    }
  }, [isScrollSensitive]);

  const handleMouseEnter = () => setDropdownVisible(true);
  const handleMouseLeave = () => setDropdownVisible(false);

  return (
    <nav
      className={`fixed top-0 left-0 w-full flex items-center py-9 px-3 z-50 transition-colors duration-300 ease-in-out border-b ${
        navbarBackground || !isScrollSensitive
          ? "bg-white border-gray-300"
          : "bg-transparent border-white/30"
      }`}
    >
      <div className="mr-24 ml-12 text-2xl">
        <a
          href="/"
          className={`${
            navbarBackground || !isScrollSensitive ? "text-black" : "text-white"
          }`}
        >
          [ WE : ]
        </a>
      </div>
      <div className="flex gap-12 justify-center flex-grow">
        <div
          className="relative"
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
        >
          <a
            href="/invitation"
            className={`${
              navbarBackground || !isScrollSensitive
                ? "text-black"
                : "text-white"
            } text-lg`}
          >
            Mobile Invitation Card
          </a>
          {dropdownVisible && (
            <div className="absolute top-full w-48 bg-white shadow-lg border">
              <a
                href="/invitation"
                className="block px-4 py-2 hover:bg-black hover:text-white"
              >
                청첩장 만들기
              </a>
              <a
                href="/invitation/storage"
                className="block px-4 py-2 hover:bg-black hover:text-white"
              >
                내 청첩장 보관함
              </a>
            </div>
          )}
        </div>
        <a
          href="/account"
          className={`${
            navbarBackground || !isScrollSensitive ? "text-black" : "text-white"
          } text-lg`}
        >
          Account Book
        </a>
        <div className="flex gap-12 ml-auto mr-12">
          <a
            href="/signup"
            className={`${
              navbarBackground || !isScrollSensitive
                ? "text-black"
                : "text-white"
            } text-lg`}
          >
            Sign Up
          </a>
          <a
            href="/login"
            className={`${
              navbarBackground || !isScrollSensitive
                ? "text-black"
                : "text-white"
            } text-lg`}
          >
            Login
          </a>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;