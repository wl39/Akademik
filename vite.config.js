import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
	allowedHosts: ['wl39.ddns.net'],
    port: 3000, // Keep CRA port
  },
});
