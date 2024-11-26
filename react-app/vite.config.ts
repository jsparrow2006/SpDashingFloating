import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import legacy from '@vitejs/plugin-legacy'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
      legacy({
        // targets: ['defaults', 'not IE 11'],
          targets: ['chrome >= 64', 'safari >= 12'],
          modernPolyfills: ['es.object.from-entries']
      }),
      react()
  ],
  server: {
    port: 8080,
  },
  build: {
    outDir: '../app/src/main/assets/www',
  },
})
