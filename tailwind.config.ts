// Copyright © 2025 Devin B. Royal. All Rights Reserved.
import type { Config } from 'tailwindcss'

const config: Config = {
  content: ['./index.html', './src/**/*.{ts,tsx}'],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        royal: '#0D1321',
        accent: '#1C2541',
        primary: '#3A506B',
        secondary: '#5BC0BE',
        danger: '#FF6B6B',
        light: '#F1F1F1'
      }
    }
  },
  plugins: []
}

export default config
