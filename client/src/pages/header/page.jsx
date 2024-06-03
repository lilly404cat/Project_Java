import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import styles from './page.module.scss';
import logoImage from '../../assets/logo.png';

export default function Header() {
  const location = useLocation();

  return (
      <>
        <header className={styles.page__header}>
          <img src={logoImage} alt="Logo" className={styles.page__header_logo}/>
          <nav className={styles.page__header_navbar}>
            <Link
                className={`${styles.page__header_navbar_link} ${location.pathname === "/departments" ? styles.active : ""}`}
                to='/departments'>Departments</Link>
            <Link
                className={`${styles.page__header_navbar_link} ${location.pathname === "/manage" ? styles.active : ""}`}
                to='/'>Manage Medicines</Link>
          </nav>
        </header>
      </>
  );
}
