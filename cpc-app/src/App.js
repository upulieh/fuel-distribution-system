import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './pages/Layout';
import Home from './pages/Home';
import OrderSubmit from './pages/OrderSubmit';
import OrderStatus from './pages/OrderStatus';
import OrderReceival from './pages/OrderReceival';
import Dispatches from './pages/Dispatches';
import NoPage from './pages/NoPage';
import OrderView from './pages/OrderView';

import './App.css';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="submit" element={<OrderSubmit />} />
          <Route path="status" element={<OrderStatus />} />
          <Route path="confirm" element={<OrderReceival />} />
          <Route path="view" element={<OrderView />} />
          <Route path="dispatches" element={<Dispatches />} />
          <Route path="*" element={<NoPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
export default App;
