import React, { useEffect, useState } from "react";

interface NavbarProps {
  isScrollSensitive?: boolean;
}

const Navbar: React.FC<NavbarProps> = ({ isScrollSensitive = false }) => {
  const [navbarBackground, setNavbarBackground] = useState(false);

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
      <div className="flex gap-12 justify-center mt-1">
        <a
          href="invitation"
          className={`${
            navbarBackground || !isScrollSensitive ? "text-black" : "text-white"
          } text-lg`}
        >
          Mobile Invitation Card
        </a>
        <a
          href="account"
          className={`${
            navbarBackground || !isScrollSensitive ? "text-black" : "text-white"
          } text-lg`}
        >
          Account Book
        </a>
        <a
          href="signup"
          className={`${
            navbarBackground || !isScrollSensitive ? "text-black" : "text-white"
          } text-lg`}
        >
          Sign Up
        </a>
        <a
          href="login"
          className={`${
            navbarBackground || !isScrollSensitive ? "text-black" : "text-white"
          } text-lg`}
        >
          Login
        </a>
      </div>
    </nav>
  );
};

export default Navbar;
