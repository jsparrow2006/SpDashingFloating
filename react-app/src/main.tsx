import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './styles/themes.scss'
import './index.scss'

import SpNative from './lib/SpNative/index'

import FloatingWindow from './containers/floatingWindow/floatingWindow'
import MyDashing from './containers/myDashing/myDashing'
import AppSettings from './containers/appSettings/appSettings'
import Test from './containers/test/test'
import NativeContainers from './lib/SpNative/containers/NativeContainers';
import TestCan from './containers/testCan/TestCan';

SpNative.initSpNativeClient()
createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <NativeContainers>
          <Router>
              <Routes>
                  <Route path="/" element={<FloatingWindow />} />
                  <Route path="/floating" element={<FloatingWindow />} />
                  <Route path="/myDashing" element={<MyDashing />}>
                      <Route path="info" element={<h2>info</h2>} />
                      <Route path="odo" element={<h2>ODO</h2>} />
                      <Route path="can" element={<TestCan />} />
                      <Route path="settings" element={<AppSettings />} />
                  </Route>
                  <Route path="/test" element={<Test />} />
                  <Route path="*" element={<h2>Not found</h2>} />
              </Routes>
          </Router>
      </NativeContainers>
  </StrictMode>,
)
