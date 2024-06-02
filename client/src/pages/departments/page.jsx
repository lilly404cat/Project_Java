import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom"; // Import Link for navigation
import Styles from "./page.module.scss";

function Departments() {
    const [departments, setDepartments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchDepartments();
    }, []); // Run this effect only once on component mount

    const fetchDepartments = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/hospital_stocks/departments', {
                method: 'GET', // Use GET request method
            });
            if (!response.ok) {
                throw new Error('Failed to fetch departments');
            }
            const data = await response.json();
            setDepartments(data);
            setLoading(false);
        } catch (error) {
            setError(error.message);
            setLoading(false);
        }
    };

    const handleDepartmentClick = async (departmentId) => {
        try {
            const response = await fetch(`API_ENDPOINT_URL/${departmentId}`, {
                method: 'HEAD', // Use HEAD request method
            });
            if (!response.ok) {
                throw new Error('Failed to load department statistics');
            }
            // Redirect to the statistics page for the selected department
            window.location.href = `/statistics/${departmentId}`;
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div className={Styles.container}>
            <div className={Styles.page__title}>
                Choose a Department to see its stocks!
            </div>
            {loading ? (
                <div>Loading...</div>
            ) : error ? (
                <div>Error: {error}</div>
            ) : (
                <div className={Styles.page__departments}>
                    {departments.map(department => (
                        <button
                            key={department.id}
                            className={Styles.page__department}
                            onClick={() => handleDepartmentClick(department.id)}
                        >
                            {department.name}
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
}

export default Departments;
