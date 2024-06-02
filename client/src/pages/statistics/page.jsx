import React from 'react';
import { useParams } from 'react-router-dom';
import Styles from './page.module.scss';

export default function Statistics() {
    const { departmentId } = useParams();

    return (
        <div className={Styles.container}>
            <h1>Statistics for Department {departmentId}</h1>
            {/* Add your statistics fetching and rendering logic here */}
        </div>
    );
}
