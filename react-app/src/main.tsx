import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './index.scss'
import App from './App.tsx'
import SpNative from './lib/SpNative/index'

import FloatingWindow from './containers/floatingWindow/floatingWindow'
import Settings from './containers/settings/settings'
import Test from './containers/test/test'

SpNative.initSpNativeClient()
createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <Router>
          <Routes>
              <Route path="/" element={<App />} />
              <Route path="/floating" element={<FloatingWindow />} />
              <Route path="/settings" element={<Settings />} />
              <Route path="/test" element={<Test />} />
              <Route path="*" element={<h2>Not found</h2>} />
          </Routes>
      </Router>
  </StrictMode>,
)
