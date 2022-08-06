import React from 'react';
import { Outlet, Link } from 'react-router-dom';
import Nav from '../components/Nav';

const Layout = () => {
  return (
    <>
      <nav>
        <div className="App">
          <Nav />
        </div>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/submit">Order Submit</Link>
          </li>
          <li>
            <Link to="/status">Order Status</Link>
          </li>
          <li>
            <Link to="/confirm">Confirm Receival</Link>
          </li>
          <li>
            <Link to="/dispatches">Dispatches</Link>
          </li>
          <li>
            <Link to="/view">View Orders</Link>
          </li>
        </ul>
      </nav>
      <Outlet />
    </>
  );
};

export default Layout;
