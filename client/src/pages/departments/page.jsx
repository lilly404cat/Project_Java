import React, { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom"; // Import Link and useNavigate for navigation
import Styles from "./page.module.scss";

function Departments() {
    const [departments, setDepartments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchDepartments();
    }, []); // Run this effect only once on component mount

    const fetchDepartments = async () => {
        try {
            const response = await fetch('http://localhost:8082/api/hospital_stocks/departments', {
                method: 'GET',
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
            const response = await fetch(`http://localhost:8082/api/hospital_stocks/departments/exists/${departmentId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.status === 200) {
                // Redirect to the statistics page for the selected department
                navigate(`/statistics/${departmentId}`);
            } else if (response.status === 404) {
                throw new Error('Department not found');
            } else {
                throw new Error('Failed to load department statistics');
            }
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
