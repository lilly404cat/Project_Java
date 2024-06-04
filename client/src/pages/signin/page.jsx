import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Styles from './page.module.scss';

const Signin = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });

    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        let encodedData = [];
        for (let property in formData) {
            let encodedKey = encodeURIComponent(property);
            let encodedValue = encodeURIComponent(formData[property]);
            encodedData.push(encodedKey + "=" + encodedValue);
        }
        encodedData = encodedData.join("&");

        try {
            const response = await fetch('http://localhost:8082/api/hospital_stocks/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: encodedData,
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(errorMessage);
            }

            const result = await response.json();

            if (result === 200) {
                navigate('/departments');
            } else {
                setError('Invalid username or password.');
            }

        } catch (error) {
            setError('An unexpected error occurred. Please try again.');
            console.log(error);
        }
    };

    return (
        <div className={Styles.container}>
            <div className={Styles.welcome__Section}>
                <h1 className={Styles.welcome__text}>Welcome to Hospital Stocks!</h1>
                <p className={Styles.welcome__subtext}>Glad to see you back!</p>
            </div>
            <div className={Styles.signin__container}>
                <h1 className={Styles.signin__title}>Sign In</h1>
                <form className={Styles.signin__form} onSubmit={handleSubmit}>
                    <label className={Styles.form__label} htmlFor="username">Your Username:</label><br />
                    <input
                        className={Styles.form__input}
                        type="text"
                        id="username"
                        name="username"
                        placeholder="Enter the username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    /><br />
                    <label className={Styles.form__label} htmlFor="password">Your Password:</label><br />
                    <input
                        className={Styles.form__input}
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Enter the password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                    <div className={Styles.submit__button}>
                        <input className={Styles.button__input} name="login" type="submit" value="Sign In" />
                    </div>
                </form>
                {error && <div className={Styles.error}>{error}</div>}
                <div className={Styles.signin__line}></div>
                <div className={Styles.not__signedup}>
                    <div className={Styles.not__signupQuestion}>Don't have an account?&nbsp;&nbsp;</div>

                    <Link className={Styles.link__signup} to="../signup">Sign Up!</Link>
                </div>
            </div>
        </div>
    );
};

export default Signin;
