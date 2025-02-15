import type { Metadata } from "next";
import { Oswald } from "next/font/google";
import "../scss/_globals.scss";
import Header from "@/components/Header";

export const metadata: Metadata = {
  title: "Билеты",
  description: "Пдд билеты"
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
      <html lang="en">
          <body>
          <Header/>
          <main>
              {children}
          </main>
          </body>
      </html>
  );
}
